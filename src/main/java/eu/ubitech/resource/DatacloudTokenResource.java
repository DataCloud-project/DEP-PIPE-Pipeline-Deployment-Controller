package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.model.DatacloudToken;
import eu.ubitech.service.DatacloudTokenService;
import eu.ubitech.utils.GenericMessageDto;
import io.quarkus.security.Authenticated;
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
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Log
@Path(Constants.TOKEN_STORAGE_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class DatacloudTokenResource {
    @Inject
    DatacloudTokenService datacloudTokenService;

    @POST
    @Path("/store")
    @Operation(summary = "Store datacloud token", description = "Save a datacloud token in the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Stored Datacloud Token",
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
    public Response store(@RequestBody DatacloudToken datacloudToken, @Context HttpServerRequest request) {
        try {
            datacloudTokenService.storeDatacloudToken(datacloudToken);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.DATACLOUD_TOKEN_STORED)).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{username}")
    @Operation(summary = "Get datacloud token", description = "Retrieve a stored datacloud token through the username that issued it")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Retrieved Datacloud Token",
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
    public Response retrieve(@PathParam("username") String username, @Context HttpServerRequest request) {
        try {
            DatacloudToken token = datacloudTokenService.getDatacloudTokenByUsername(username);
            return Response.ok().entity(token).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

}
