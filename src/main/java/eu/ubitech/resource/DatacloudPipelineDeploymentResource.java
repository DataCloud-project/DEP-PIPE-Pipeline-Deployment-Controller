package eu.ubitech.resource;

import eu.ubitech.service.DatacloudPipelineDeploymentService;
import eu.ubitech.utils.GenericMessageDto;
import eu.ubitech.utils.MaestroRestResponseDto;
import io.quarkus.security.Authenticated;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.java.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Log
@Path("/dc/api/v1/datacloud/pipelinedeployment/{pipelineDeploymentID}/request")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class DatacloudPipelineDeploymentResource {

    @Inject
    DatacloudPipelineDeploymentService datacloudPipelineDeploymentService;

    /* Request Pipeline Deployment */
    @POST
    @Operation(summary = "Request Deployment of Datacloud Pipeline", description = "Send a request to initialize deployment of datacloud pipeline")
    @Parameter(name = "pipelineDeploymentID", description = "The id value of the pipeline deployment in the database", required = true)
    @Path("/deployment")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Requested Deployment of Datacloud Pipeline",
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
    public Response requestDeployment(@PathParam("pipelineDeploymentID") Long pipelineDeploymentID, @Context HttpServerRequest request) {
        try {
            Response res = datacloudPipelineDeploymentService.requestDatacloudPipelineDeployment(pipelineDeploymentID);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PIPELINE_DEPLOYMENT_REQUESTED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Request Pipeline Undeployment */
    @POST
    @Operation(summary = "Request Undeployment of Datacloud Pipeline", description = "Send a request to initialize undeployment of datacloud pipeline")
    @Parameter(name = "pipelineDeploymentID", description = "The id value of the pipeline deployment in the database", required = true)
    @Path("/undeployment")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Requested Undeployment of Datacloud Pipeline",
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
    public Response requestUndeployment(@PathParam("pipelineDeploymentID") Long pipelineDeploymentID, @Context HttpServerRequest request) {
        try {
            Response res = datacloudPipelineDeploymentService.requestDatacloudPipelineUndeployment(pipelineDeploymentID);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PIPELINE_UNDEPLOYMENT_REQUESTED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Request Pipeline Deployment Cancellation */
    @POST
    @Operation(summary = "Request Deployment Cancellation of Datacloud Pipeline", description = "Send a request to cancel deployment of datacloud pipeline")
    @Parameter(name = "pipelineDeploymentID", description = "The id value of the pipeline deployment in the database", required = true)
    @Path("/cancellation")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Requested Deployment Cancellation of Datacloud Pipeline",
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
    public Response requestCancellation(@PathParam("pipelineDeploymentID") Long pipelineDeploymentID, @Context HttpServerRequest request) {
        try {
            Response res = datacloudPipelineDeploymentService.requestDatacloudPipelineCancellation(pipelineDeploymentID);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PIPELINE_CANCELLATION_REQUESTED)).build();
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
