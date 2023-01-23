package eu.ubitech.clients;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.ubitech.transfer.entities.DatacloudChunkTo;
import eu.ubitech.transfer.entities.DatacloudPipelineTo;
import eu.ubitech.transfer.entities.DatacloudStepTo;
import eu.ubitech.utils.MaestroAuthDto;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1")
@RegisterClientHeaders
@RegisterRestClient(configKey = "maestro-rest-api")
public interface MaestroRestClient {

    @POST
    @Path("/auth/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response maestroAuthenticate(@RequestBody MaestroAuthDto maestroAuthDto);

    @POST
    @Path("/datacloud")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createStep(@CookieParam("auth_token") String authToken, @RequestBody DatacloudStepTo datacloudStepTo);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void deletePipeline(@PathParam("id") Long id);

    @POST
    @Path("/datacloud/step")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createDatacloudStep(@CookieParam("auth_token") String authToken, @RequestBody DatacloudStepTo datacloudStepTo);

    @PUT
    @Path("/datacloud/step")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateDatacloudStep(@CookieParam("auth_token") String authToken, @RequestBody DatacloudStepTo datacloudStepTo);

    @GET
    @Path("/datacloud/step/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getDatacloudStep(@CookieParam("auth_token") String authToken, @PathParam("id") Long id);

    @DELETE
    @Path("/datacloud/step/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteDatacloudStep(@CookieParam("auth_token") String authToken, @PathParam("id") Long id);

    @POST
    @Path("/datacloud/chunk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createDatacloudChunk(@CookieParam("auth_token") String authToken, @RequestBody DatacloudChunkTo datacloudChunkTo);

    @PUT
    @Path("/datacloud/chunk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateDatacloudChunk(@CookieParam("auth_token") String authToken, @RequestBody DatacloudChunkTo datacloudChunkTo);

    @GET
    @Path("/datacloud/chunk/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getDatacloudChunk(@CookieParam("auth_token") String authToken, @PathParam("id") Long id);

    @DELETE
    @Path("/datacloud/chunk/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteDatacloudChunk(@CookieParam("auth_token") String authToken, @PathParam("id") Long id);

    @POST
    @Path("/datacloud/pipeline")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createDatacloudPipeline(@CookieParam("auth_token") String authToken, @RequestBody DatacloudPipelineTo datacloudPipelineTo);

    @PUT
    @Path("/datacloud/pipeline")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateDatacloudPipeline(@CookieParam("auth_token") String authToken, @RequestBody DatacloudPipelineTo datacloudPipelineTo);

    @GET
    @Path("/datacloud/pipeline/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getDatacloudPipeline(@CookieParam("auth_token") String authToken, @PathParam("id") Long id);

    @DELETE
    @Path("/datacloud/pipeline/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteDatacloudPipeline(@CookieParam("auth_token") String authToken, @PathParam("id") Long id);

    @POST
    @Path("/datacloud/pipelinedeployment/{pipelineDeploymentID}/request/deployment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response requestDeployment(@CookieParam("auth_token") String authToken, @PathParam("pipelineDeploymentID") Long id);

    @POST
    @Path("/datacloud/pipelinedeployment/{pipelineDeploymentID}/request/undeployment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response requestUndeployment(@CookieParam("auth_token") String authToken, @PathParam("pipelineDeploymentID") Long id);

    @POST
    @Path("/datacloud/pipelinedeployment/{pipelineDeploymentID}/request/cancellation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response requestCancellation(@CookieParam("auth_token") String authToken, @PathParam("pipelineDeploymentID") Long id);

}

