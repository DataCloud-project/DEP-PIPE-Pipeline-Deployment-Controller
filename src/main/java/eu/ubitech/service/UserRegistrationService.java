package eu.ubitech.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.exception.AccountNotFullySetUpException;
import eu.ubitech.exception.InvalidUserCredentialsException;
import eu.ubitech.utils.ErrorWrapper;
import eu.ubitech.utils.KeycloakAuthTokenDto;
import eu.ubitech.utils.UserDto;
import eu.ubitech.utils.UserRegistrationDto;
import lombok.extern.java.Log;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@ApplicationScoped
@Log
public class UserRegistrationService {
    //TODO add the config property instead of hard coded assignments
    @ConfigProperty(name = "KEYCLOAK_URL")
    String keycloakUrl;
    @ConfigProperty(name = "KEYCLOAK_REALM")
    String keycloakRealm;
    @ConfigProperty(name = "KEYCLOAK_CLIENT_ID")
    String keycloakClientId;
    @ConfigProperty(name = "KEYCLOAK_CLIENT_SECRET")
    String keycloakClientSecret;
    @ConfigProperty(name = "KEYCLOAK_ADMIN_USERNAME")
    String keycloakAdminUsername;
    @ConfigProperty(name = "KEYCLOAK_ADMIN_PASSWORD")
    String keycloakAdminPassword;
    @ConfigProperty(name = "KEYCLOAK_ADMIN_CLIENT")
    String keycloakAdminClient;
    @ConfigProperty(name = "KEYCLOAK_ADMIN_REALM")
    String keycloakAdminRealm;



    private static final String CONTENT_TYPE="content-type";
    private static final String PASSWORD="password";
    private static final String GRANT_TYPE="grant_type";
    private static final String AUTHORIZATION="authorization";
    private static final String BEARER="bearer ";
    private static final String JSON_HEADER="application/json";
    private static final String CLIENT_ID = "client_id";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String X_FORM_ENCODED = "application/x-www-form-urlencoded";
    private static final String AUTH_PATH = "/auth/realms/";


    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response createDatacloudUser(UserDto userDto) {
        try {
            // Auth token not necessary at the moment
//            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.createDatacloudUser(userDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String registerKeycloakUser(UserRepresentation keycloakUser, List<String> realmRoles, String password) throws Exception {
        var keycloak = keycloak();
        var realmResource = keycloak.realm(keycloakRealm);
        var usersResource = realmResource.users();
        //---Step 2 Request to add User -----------------
        var response = usersResource.create(keycloakUser);
        log.log(Level.INFO, "Response: {0} {1}%n", new Object[]{response.getStatus(), response.getStatusInfo()});



        if (response.getStatus() != 201) throw new Exception();

        String userId = CreatedResponseUtil.getCreatedId(response);
        //---Step 4 Assign roles for userId ---------------
        var userResource = usersResource.get(userId);
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        for (String roleName : realmRoles) {
            RoleRepresentation realmRole = realmResource.roles().get(roleName).toRepresentation();
            roleRepresentations.add(realmRole);
        }
        userResource.roles().realmLevel().add(roleRepresentations);
        keycloak.close();
        //---Step 4 Reset User's Password-----------------
        resetPasswordKeycloak(userId, password);

        return userId;

    }

    public UserRepresentation createKeycloakUser(UserRegistrationDto userRegistrationDto) throws Exception {

        // Check password matching
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword()))
            throw new Exception("Passwords do not match");

        // Check username existence
        usernameExists(userRegistrationDto.getUsername());

        // Check email existence
        emailExists(userRegistrationDto.getEmail());

        // Create UserRepresentation
        var keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(userRegistrationDto.getUsername());
        keycloakUser.setEmail(userRegistrationDto.getEmail());
        keycloakUser.setFirstName(userRegistrationDto.getFirstName());
        keycloakUser.setLastName(userRegistrationDto.getLastName());
        keycloakUser.setEnabled(true);

        // Create Attributes if necessary
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("maestro_username", Collections.singletonList(userRegistrationDto.getUsername()));
        attributes.put("maestro_password", Collections.singletonList(userRegistrationDto.getPassword()));
        keycloakUser.setAttributes(attributes);

        // Create Keycloak User with attributes and UserRepresentation
        String userId = registerKeycloakUser(keycloakUser, userRegistrationDto.getRealmRoles(), userRegistrationDto.getPassword());
        keycloakUser.setId(userId);

        return keycloakUser;
    }


    private void emailExists(String email) throws Exception {
        UserRepresentation tempUser = null;
        try {
            tempUser = findUser(email);
        } catch(Exception ignored){
            // Empty on purpose
        }
        if (tempUser != null) throw new Exception("Email already exists");
    }

    private void usernameExists(String username) throws Exception {
        UserRepresentation tempUser = null;
        try {
            tempUser = findUser(username);
        } catch(Exception ignored){
            // Empty on purpose
        }
        if (tempUser != null) throw new Exception("Username already exists");
    }


    public UserRepresentation findUser(String attribute) throws Exception {
        var keycloak = keycloak();
        var realmResource = keycloak.realm(keycloakRealm);
        UsersResource userResource = realmResource.users();

        // The searching is being done as a like query not with the exact attribute provided
        // It returns the first user that has the attribute as a string either in the name or email
        List<UserRepresentation> users = userResource.search(attribute, 0, 10);
        keycloak.close();
        // Search users in order to match with the exact attribute provided
        for(UserRepresentation user: users) {
            if(user.getEmail().equals(attribute) || user.getUsername().equals(attribute)) return users.get(0);
        }

        log.log(Level.WARNING,"User with attribute: {0} does not exist in keycloak", attribute);
        throw new Exception("User with attribute:  does not exist in keycloak");
    }

    public void resetPasswordKeycloak(String keycloakUserId, String newPassword) {
        log.info("Request to reset user password");

        // Set password credential
        var passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(newPassword);

        var keycloak = keycloak();
        var realmResource = keycloak.realm(keycloakRealm);
        var usersResource = realmResource.users();
        usersResource.get(keycloakUserId).resetPassword(passwordCred);
        keycloak.close();
    }

    public void sendResetPasswordEmail(String username) throws Exception {
        var user = findUser(username);
        if (user == null) throw new Exception("user");

        try {
            sendKeycloakEmail(user, "UPDATE_PASSWORD");
        } catch (Exception e){
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public void sendVerificationEmail(String username) throws Exception {
        var user = findUser(username);
        if (user == null) throw new Exception("user");

        try {
            sendKeycloakEmail(user, "VERIFY_EMAIL");
        } catch (Exception e){
            log.log(Level.WARNING, e.getMessage());
        }
    }

    public KeycloakAuthTokenDto getAdminAccessToken() {
        KeycloakAuthTokenDto authToken;
        String tokenEndpoint = keycloakUrl + AUTH_PATH + keycloakAdminRealm + "/protocol/openid-connect/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(CLIENT_ID, keycloakAdminClient));
            params.add(new BasicNameValuePair("username", keycloakAdminUsername));
            params.add(new BasicNameValuePair(PASSWORD, keycloakAdminPassword));
            params.add(new BasicNameValuePair(GRANT_TYPE, PASSWORD));
            var post = new HttpPost(tokenEndpoint);
            post.setEntity(new UrlEncodedFormEntity(params));

            // Get the response code
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                log.info("Token received successfully");
                var jsonString = EntityUtils.toString(response.getEntity());
                var mapper = new ObjectMapper();
                //JSON from file to Object
                authToken = mapper.readValue(jsonString, KeycloakAuthTokenDto.class);
            } else {
                log.severe("Error during token fetching");
                authToken = null;
            }
        } catch (Exception ex) {
            log.severe("Error during token fetching" + ex.getMessage());
            authToken = null;
        }
        return authToken;
    }


    private void sendKeycloakEmail(UserRepresentation userRepresentation, String parameter) throws  IOException {
        String endpoint = keycloakUrl + "/auth/admin/realms/platform/users/"+userRepresentation.getId()+"/execute-actions-email";
        var authToken = getAdminAccessToken();
        var put = new HttpPut(endpoint);
        final HttpClient client = HttpClientBuilder.create().build();

        put.setHeader(HttpHeaders.AUTHORIZATION, BEARER + authToken.getToken());
        put.setHeader(CONTENT_TYPE, JSON_HEADER);
        put.setEntity(new StringEntity("[\""+parameter+"\"]","UTF-8"));

        client.execute(put);
    }

    public KeycloakAuthTokenDto login(String username, String password) {
        log.log(Level.INFO,"Fetching token for: {0}", username);
        String tokenEndpoint = keycloakUrl + AUTH_PATH + keycloakRealm + "/protocol/openid-connect/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            var post = new HttpPost(tokenEndpoint);
            var encoding = Base64.getEncoder().encodeToString((keycloakClientId + ":" + keycloakClientSecret).getBytes());
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
            post.setHeader(CONTENT_TYPE, X_FORM_ENCODED);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair(PASSWORD, password));
            params.add(new BasicNameValuePair(GRANT_TYPE, PASSWORD));
            post.setEntity(new UrlEncodedFormEntity(params));

            // Get the response code
            HttpResponse response = client.execute(post);

            var jsonString = EntityUtils.toString(response.getEntity());
            log.info("Login attempt response code: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200) {
                if (response.getStatusLine().getStatusCode() == 401) {
                    throw new InvalidUserCredentialsException();
                } else if (response.getStatusLine().getStatusCode() == 400) {
                    UserRepresentation keycloakUser = findUser(username);
                    var errorWrapper = (new ObjectMapper()).readValue(jsonString, ErrorWrapper.class);
                    var requiredAction = "";
                    if (keycloakUser.getRequiredActions() != null && !keycloakUser.getRequiredActions().isEmpty())
                        requiredAction = "." + keycloakUser.getRequiredActions().get(0).toLowerCase();
                    else if (keycloakUser.isEnabled().equals(Boolean.FALSE)) requiredAction = "." + "user_disabled";

                    throw new AccountNotFullySetUpException(errorWrapper.getErrorDescription() + ": " + String.join(", ",
                            keycloakUser.getRequiredActions()), errorWrapper.getError() + requiredAction);
                } else {
                    log.info(jsonString);
                    throw new InternalServerErrorException();
                }
            }
            var mapper = new ObjectMapper();
            //JSON from file to Object
            return mapper.readValue(jsonString, KeycloakAuthTokenDto.class);
        } catch (IOException e) {
            throw new InternalServerErrorException();
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }



    private Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakUrl + "/auth")
                .realm(keycloakAdminRealm)
                .username(keycloakAdminUsername)
                .password(keycloakAdminPassword)
                .clientId(keycloakAdminClient)
                .build();
    }
}
