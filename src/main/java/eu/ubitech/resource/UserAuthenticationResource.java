package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.UserAuthenticationService;
import eu.ubitech.transfer.UserLoginDto;
import eu.ubitech.utils.GenericMessageDto;
import eu.ubitech.utils.KeycloakAuthTokenDto;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.java.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Log
@Path(Constants.USER_AUTH_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAuthenticationResource {

    @Inject
    UserAuthenticationService userAuthenticationService;

    /* Register Keycloak User */
    @POST
    @Path("/login/keycloak")
    @Operation(summary = "Login datacloud keycloak user", description = "Login keycloak user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successful login Datacloud Keycloak User",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "401", description = "Request Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "403", description = "Request Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            )
    })
    public Response loginKeycloakUser(@RequestBody UserLoginDto userLoginDto, @Context HttpServerRequest request) {
        try {
            KeycloakAuthTokenDto keycloakAuthTokenDto = userAuthenticationService.loginKeycloakUser(userLoginDto.getUsername(), userLoginDto.getPassword());
//            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.KEYCLOAK_USER_LOGGED_IN)).build();
            return Response.ok().entity(keycloakAuthTokenDto).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            return Response.serverError().entity(new GenericMessageDto(eb.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @POST
    @Path("/login/dataclouddep")
    @Operation(summary = "Login datacloud dep user", description = "Login datacloud dep user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successful Login Datacloud DEP User",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "401", description = "Request Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "403", description = "Request Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            )
    })
    public Response loginDatacloudUser(@RequestBody UserLoginDto userLoginDto, @Context HttpServerRequest request) {
        try {
            NewCookie cookie = userAuthenticationService.loginDatacloudUser(userLoginDto.getUsername(), userLoginDto.getPassword());
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.DATACLOUD_USER_CREATED))
                    .cookie(cookie).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            return Response.serverError().entity(new GenericMessageDto(eb.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @POST
    @Path("/login/global")
    @Operation(summary = "Login keycloak and datacloud dep and  user", description = "Login keycloak and datacloud dep user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successful Login for Keycloak and Datacloud DEP User",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "401", description = "Request Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "403", description = "Request Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            ),
            @APIResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenericMessageDto.class)
                    )
            )
    })
    public Response loginKeycloakAndDatacloudUser(@RequestBody UserLoginDto userLoginDto, @Context HttpServerRequest request) {
        try {
             NewCookie cookie = userAuthenticationService.loginDatacloudUser(userLoginDto.getUsername(), userLoginDto.getPassword());
             KeycloakAuthTokenDto keycloakAuthTokenDto = userAuthenticationService.loginKeycloakUser(userLoginDto.getUsername(), userLoginDto.getPassword());
            // TODO consider using service function for concurrent login
//            NewCookie cookie = userAuthenticationService.loginKeycloakAndDatacloudUser(userLoginDto.getUsername(), userLoginDto.getPassword());
            return Response.ok()
                    .cookie(cookie).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            return Response.serverError().entity(new GenericMessageDto(eb.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }


}
