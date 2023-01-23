package eu.ubitech.clients;

import eu.ubitech.transfer.WorkflowTemplateDto;
import io.quarkus.oidc.client.reactive.filter.OidcClientRequestReactiveFilter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// TODO Reactive endpoints
@Path("/")
@RegisterClientHeaders
@RegisterProvider(OidcClientRequestReactiveFilter.class)
@RegisterRestClient(configKey = "defpipe-rest-api")
public interface DefPipeRestClient {

    @GET
    @Path("/repo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response getWorkflowByUserAndByName(@QueryParam("user") String user, @QueryParam("workflowName") String workflowName);

    @GET
    @Path("/repo/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response getUserWorkflows(@PathParam("user") String user);

    @POST
    @Path("/repo/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response createWorkflow(@PathParam("user") String user, @RequestBody WorkflowTemplateDto workflowTemplateDto);

    @DELETE
    @Path("/repo/{user}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response deleteWorkflowByUserAndById(@PathParam("user") String user, @PathParam("id") String id);

}
