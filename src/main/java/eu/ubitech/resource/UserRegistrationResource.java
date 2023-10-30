package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.mapper.UserMapper;
import eu.ubitech.service.UserRegistrationService;
import eu.ubitech.utils.GenericMessageDto;
import eu.ubitech.utils.UserDto;
import eu.ubitech.utils.UserRegistrationDto;
import io.quarkus.security.Authenticated;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.java.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.keycloak.representations.idm.UserRepresentation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Log
@Path(Constants.USER_REGISTRATION_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class UserRegistrationResource {

    @Inject
    UserRegistrationService userRegistrationService;

    @Inject
    UserMapper userMapper;

    /* Register Keycloak User */
    @POST
    @Path("/keycloak")
    @Operation(summary = "Create datacloud keycloak user", description = "Register keycloak user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Datacloud Keycloak User",
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
    public Response registerKeycloakUser(@RequestBody UserRegistrationDto userRegistrationDto, @Context HttpServerRequest request) {
        try {
            UserRepresentation userRepresentation = userRegistrationService.createKeycloakUser(userRegistrationDto);
            // Currently not needed, save for future
            // userRegistrationService.sendVerificationEmail(userRegistrationDto.getUsername());
            // userRegistrationService.joinGroup(userRegistrationDto.getUsername(), userRepresentation.getId(), userRegistrationDto.getOrganization());
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.KEYCLOAK_USER_CREATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            return Response.serverError().entity(new GenericMessageDto(eb.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @POST
    @Path("/dataclouddep")
    @Operation(summary = "Create datacloud dep user", description = "Register datacloud dep user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Datacloud DEP User",
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
    public Response registerDatacloudUser(@RequestBody UserDto userDto, @Context HttpServerRequest request) {
        try {
            Response res = userRegistrationService.createDatacloudUser(userDto);
            // Currently not needed, save for future
            // userRegistrationService.sendVerificationEmail(userRegistrationDto.getUsername());
            // userRegistrationService.joinGroup(userRegistrationDto.getUsername(), userRepresentation.getId(), userRegistrationDto.getKeycloakOrganization());
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.DATACLOUD_USER_CREATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            return Response.serverError().entity(new GenericMessageDto(eb.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @POST
    @Path("/global")
    @Operation(summary = "Create keycloak and datacloud dep and  user", description = "Register keycloak and datacloud dep user")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Keycloak and Datacloud DEP User",
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
    public Response registerKeycloakAndDatacloudUser(@RequestBody UserRegistrationDto userRegistrationDto, @Context HttpServerRequest request) {
        try {
            UserDto userDto = userMapper.toUserDto(userRegistrationDto);
            Response res = userRegistrationService.createDatacloudUser(userDto);
            UserRepresentation userRepresentation = userRegistrationService.createKeycloakUser(userRegistrationDto);
            // Currently not needed, save for future
            // userRegistrationService.sendVerificationEmail(userRegistrationDto.getUsername());
            // userRegistrationService.joinGroup(userRegistrationDto.getUsername(), userRepresentation.getId(), userRegistrationDto.getKeycloakOrganization());
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.KEYCLOAK_AND_DATACLOUD_USER_CREATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            return Response.serverError().entity(new GenericMessageDto(eb.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

}
