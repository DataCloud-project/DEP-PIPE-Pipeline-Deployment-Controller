package eu.ubitech.service;

import eu.ubitech.utils.KeycloakAuthTokenDto;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.NewCookie;
import java.util.Date;

@ApplicationScoped
@Log
public class UserAuthenticationService {
   @Inject
   UserRegistrationService userRegistrationService;

    @Inject
    MaestroAuthService maestroAuthService;

    public KeycloakAuthTokenDto loginKeycloakUser(String username, String password) {
         KeycloakAuthTokenDto keycloakAuthTokenDto = userRegistrationService.login(username, password);
         return keycloakAuthTokenDto;
    }

    public NewCookie loginDatacloudUser(String username, String password) throws Exception {
        String authTokenCookie = maestroAuthService.maestroAuthenticateWithCredentials(username, password);
        Date expiration = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 24 hours
        NewCookie cookie = new NewCookie("auth_token", authTokenCookie, "/", "localhost", 1, "Cookie Description", 3600, expiration, false, false);
    //        cookie.setHttpOnly(true);
    //        cookie.setMaxAge(0);
    //        cookie.setPath("/");
        return cookie;
    }

    public NewCookie loginKeycloakAndDatacloudUser(String username, String password) throws Exception {
            KeycloakAuthTokenDto keycloakAuthTokenDto = loginKeycloakUser(username, password);
            NewCookie newCookie = loginDatacloudUser(username,password);
            return newCookie;
    }


}
