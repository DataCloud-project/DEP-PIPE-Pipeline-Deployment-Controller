package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.MaestroKubernetesService;
import eu.ubitech.transfer.StepNodeInstanceAffinityDto;
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
@Path(Constants.MAESTRO_KUBERNETES_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class MaestroKubernetesResource {

    @Inject
    MaestroKubernetesService maestroKubernetesService;

    /* Get Maestro-K8s cluster labels by provider id */
    @GET
    @Path("/cluster/labels/provider/{providerId}")
    @Operation(summary = "Fetch maestro-k8s cluster labels by provider", description = "Get maestro-k8s cluster labels by provider id")
    @Parameter(name = "providerId", description = "The id value of the datacloud provider in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Maestro-k8s Cluster Labels",
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
    public Response fetchK8sLabelsByProviderId(@PathParam("providerId") Long providerId, @Context HttpServerRequest request) {
        try {
            Response res = maestroKubernetesService.getClusterLabels(providerId);
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


    @POST
    @Path("/{chunkId}/stepnodeinstance/{sniID}/affinity")
    @Operation(summary = "Create step node instance affinity", description = "Create step node instance affinity")
    @Parameter(name = "chunkId", description = "The id value of the datacloud provider in the database", required = true)
    @Parameter(name = "sniID", description = "The id value of the datacloud provider in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Step Node Instance Affinity",
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
    public Response createStepNodeInstanceAffinity(@PathParam("chunkId") Long chunkId, @PathParam("sniID") Long sniId,
                          @RequestBody StepNodeInstanceAffinityDto sniAffinityDto, @Context HttpServerRequest request) {
        try {
            Response res = maestroKubernetesService.createStepNodeInstanceAffinity(chunkId, sniId, sniAffinityDto);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();

//            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.STEP_NODE_INSTANCE_AFFINITY_CREATED)).build();
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