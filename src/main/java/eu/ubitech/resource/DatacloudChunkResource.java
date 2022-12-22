package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.DatacloudChunkService;
import eu.ubitech.transfer.entities.DatacloudChunkTo;
import eu.ubitech.utils.GenericMessageDto;
import eu.ubitech.utils.MaestroRestResponseDto;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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

//TODO Add logging
@Log
@Path(Constants.CHUNK_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DatacloudChunkResource {

    @Inject
    DatacloudChunkService datacloudChunkService;

    @ConfigProperty(name = "maestro.auth.token")
    String authToken;

    /* Get Chunk */
    @GET
    @Path("/{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Datacloud Chunk",
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
            Response res = datacloudChunkService.getDatacloudChunk(authToken, id);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Create Chunk */
    @POST
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Datacloud Chunk",
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
    public Response create(@RequestBody DatacloudChunkTo datacloudChunkTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudChunkService.createDatacloudChunk(authToken, datacloudChunkTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.CHUNK_CREATED)).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Update Chunk */
    @PUT
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Updated Datacloud Chunk",
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
    public Response update(@RequestBody DatacloudChunkTo datacloudChunkTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudChunkService.updateDatacloudChunk(authToken, datacloudChunkTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.CHUNK_UPDATED)).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Delete Chunk */
    @DELETE
    @Path("/{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Deleted Datacloud Chunk",
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
            Response res = datacloudChunkService.deleteDatacloudChunk(authToken, id);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.CHUNK_DELETED)).build();
        } catch (WebApplicationException eb) {
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }


}
