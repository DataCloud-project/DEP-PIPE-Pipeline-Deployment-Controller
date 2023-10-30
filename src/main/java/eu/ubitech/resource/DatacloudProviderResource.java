package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.service.DatacloudProviderService;
import eu.ubitech.transfer.entities.DatacloudProviderTo;
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
@Path(Constants.PROVIDER_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class DatacloudProviderResource {

    @Inject
    DatacloudProviderService datacloudProviderService;

    /* Get Provider */
    @GET
    @Path("/{id}")
    @Operation(summary = "Fetch datacloud provider", description = "Get a datacloud provider by id")
    @Parameter(name = "id", description = "The id value of the datacloud provider in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Datacloud Provider",
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
            Response res = datacloudProviderService.getDatacloudProvider(id);
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

    /* Create Provider */
    @POST
    @Operation(summary = "Create datacloud provider", description = "Create a datacloud provider")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created Datacloud Provider",
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
    public Response create(@RequestBody DatacloudProviderTo datacloudProviderTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderService.createDatacloudProvider(datacloudProviderTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PROVIDER_CREATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Update Provider */
    @PUT
    @Operation(summary = "Update datacloud provider", description = "Update a datacloud provider")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Updated Datacloud provider",
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
    public Response update(@RequestBody DatacloudProviderTo datacloudProviderTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderService.updateDatacloudProvider(datacloudProviderTo);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PROVIDER_UPDATED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Delete Provider */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete datacloud provider", description = "Delete a datacloud provider by id")
    @Parameter(name = "id", description = "The id value of the datacloud provider in the database", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Deleted Datacloud Provider",
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
            Response res = datacloudProviderService.deleteDatacloudProvider(id);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PROVIDER_DELETED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Update Provider */
    @PUT
    @Path("/{id}/default")
    @Operation(summary = "Change datacloud provider default status", description = "Change the default status of a datacloud provider")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Changed Datacloud Provider Default Status",
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
    public Response changeDefaultStatus(@PathParam("id") Long id, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderService.changeDefaultStatus(id);
            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PROVIDER_DEFAULT_STATUS_CHANGED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Fetch Providers */
    @POST
    @Path("/list")
    @Operation(summary = "List datacloud providers", description = "List all datacloud providers")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Fetched Datacloud Providers",
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
    public Response fetchProviders(@QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("sort") String sort, @QueryParam("filters") String filters, @RequestBody(required = false) DatacloudProviderTo datacloudProviderTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderService.fetchDatacloudProviders(page, size, sort, filters, datacloudProviderTo);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();
//            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PROVIDER_LIST_FETCHED)).build();
        } catch (WebApplicationException eb) {
            log.log(Level.WARNING, eb.getMessage());
            MaestroRestResponseDto maestroRestResponseDto = eb.getResponse().readEntity(MaestroRestResponseDto.class);
            return Response.serverError().entity(new GenericMessageDto(maestroRestResponseDto.getMessage())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    /* Fetch Providers for Deployment */
    @POST
    @Path("/list/deployment")
    @Operation(summary = "List datacloud providers", description = "List all datacloud providers")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Fetched Datacloud Providers",
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
    public Response fetchProvidersForDeployment(@QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("sort") String sort, @QueryParam("filters") String filters, @RequestBody(required = false) DatacloudProviderTo datacloudProviderTo, @Context HttpServerRequest request) {
        try {
            Response res = datacloudProviderService.fetchDatacloudProvidersForDeployment(page, size, sort, filters, datacloudProviderTo);
            MaestroRestResponseDto maestroRestResponseDto = res.readEntity(MaestroRestResponseDto.class);
            return Response.ok().entity(maestroRestResponseDto).build();
//            return Response.ok().entity(new GenericMessageDto(GenericMessageDto.PROVIDER_LIST_FOR_DEPLOYMENT_FETCHED)).build();
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
