package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.DatacloudStepService;
import eu.ubitech.transfer.entities.DatacloudStepTo;
import eu.ubitech.utils.GenericMessageDto;
import eu.ubitech.utils.MaestroRestResponseDto;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//TODO Add logging
@Log
@Path(Constants.STEP_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DatacloudStepResource {

    @Inject
    DatacloudStepService datacloudStepService;

    @ConfigProperty(name = "maestro.auth.token")
    String authToken;

    /* Get Step */
    @GET
    @Path("/{id}")
    @Operation(summary = "Fetch datacloud step", description = "Get a datacloud step by id")
    @Parameter(name = "id", description = "The id value of the datacloud step in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Datacloud Step",
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
            Response res = datacloudStepService.getDatacloudStep(authToken, id);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Create Step */
    @POST
    @Operation(summary = "Create datacloud step", description = "Create a datacloud step")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Datacloud Step",
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
    public Response create(@RequestBody DatacloudStepTo datacloudStepTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudStepService.createDatacloudStep(authToken, datacloudStepTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.STEP_CREATED)).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Update Step */
    @PUT
    @Operation(summary = "Update datacloud step", description = "Update a datacloud step")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Updated Datacloud Step",
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
    public Response update(@RequestBody DatacloudStepTo datacloudStepTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudStepService.updateDatacloudStep(authToken, datacloudStepTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.STEP_UPDATED)).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Delete Step */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete datacloud step", description = "Delete a datacloud step by id")
    @Parameter(name = "id", description = "The id value of the datacloud step in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Deleted Datacloud Step",
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
            Response res = datacloudStepService.deleteDatacloudStep(authToken, id);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.STEP_DELETED)).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

}


