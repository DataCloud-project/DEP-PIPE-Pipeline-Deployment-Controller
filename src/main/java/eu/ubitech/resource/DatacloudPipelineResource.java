package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.DatacloudPipelineService;
import eu.ubitech.transfer.entities.DatacloudPipelineTo;
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
@Path(Constants.PIPELINE_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class DatacloudPipelineResource {

    @Inject
    DatacloudPipelineService datacloudPipelineService;

    /* Get Pipeline */
    @GET
    @Path("/{id}")
    @Operation(summary = "Fetch datacloud pipeline", description = "Get a datacloud pipeline by id")
    @Parameter(name = "id", description = "The id value of the datacloud pipeline in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Datacloud Pipeline",
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
            Response res = datacloudPipelineService.getDatacloudPipeline(id);
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

    /* Create Pipeline */
    @POST
    @Operation(summary = "Create datacloud pipeline", description = "Create a datacloud pipeline")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Datacloud Pipeline",
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
    public Response create(@RequestBody DatacloudPipelineTo datacloudPipelineTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudPipelineService.createDatacloudPipeline(datacloudPipelineTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PIPELINE_CREATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Update Pipeline */
    @PUT
    @Operation(summary = "Update datacloud pipeline", description = "Update a datacloud pipeline")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Updated Datacloud Pipeline",
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
    public Response update(@RequestBody DatacloudPipelineTo datacloudPipelineTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudPipelineService.updateDatacloudPipeline(datacloudPipelineTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PIPELINE_UPDATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Delete Pipeline */
    @DELETE
    @Operation(summary = "Delete datacloud pipeline", description = "Delete a datacloud pipeline by id")
    @Parameter(name = "id", description = "The id value of the datacloud pipeline in the database", required = true)
    @Path("/{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Deleted Datacloud Pipeline",
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
    public Response delete(@PathParam("id") Long id, @Context HttpServerRequest request) {
        try {
            Response res = datacloudPipelineService.deleteDatacloudPipeline(id);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PIPELINE_DELETED)).build();
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
