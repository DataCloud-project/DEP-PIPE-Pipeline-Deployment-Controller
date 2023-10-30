package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.DatacloudProviderTypeService;
import eu.ubitech.transfer.entities.DatacloudProviderTypeTo;
import eu.ubitech.utils.GenericMessageDto;
import eu.ubitech.utils.MaestroRestResponseDto;
import io.quarkus.security.Authenticated;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.java.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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
@Path(Constants.PROVIDER_TYPE_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class DatacloudProviderTypeResource {

    @Inject
    DatacloudProviderTypeService datacloudProviderTypeService;

    /* Get Provider Type */
    @GET
    @Path("/{id}")
    @Operation(summary = "Fetch datacloud provider type", description = "Get a datacloud provider type by id")
    @Parameter(name = "id", description = "The id value of the datacloud provider type in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Datacloud Provider Type",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MaestroRestResponseDto.class)
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
    public Response fetchById(@PathParam("id") Long id, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderTypeService.getDatacloudProviderType(id);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }


    /* Fetch Provider Types */
    @POST
    @Path("/list")
    @Operation(summary = "List datacloud provider types", description = "List all datacloud provider types")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Fetched Datacloud Provider Types",
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
    public Response fetchProviderTypes(@QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("sort") String sort, @QueryParam("filters") String filters, @RequestBody(required = false) DatacloudProviderTypeTo datacloudProviderTypeTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderTypeService.fetchDatacloudProviderTypes(page, size, sort, filters, datacloudProviderTypeTo);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

}
