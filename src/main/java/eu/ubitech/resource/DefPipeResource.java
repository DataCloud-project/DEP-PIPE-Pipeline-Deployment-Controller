package eu.ubitech.resource;

import eu.ubitech.constants.Constants;
import eu.ubitech.mapper.WorkflowMapper;
import eu.ubitech.model.Workflow;
import eu.ubitech.service.WorkflowService;
import eu.ubitech.transfer.UserWorkflowsDto;
import eu.ubitech.transfer.WorkflowDto;
import eu.ubitech.transfer.WorkflowTemplateDto;
import eu.ubitech.utils.DefPipeRestResponseDto;
import eu.ubitech.utils.GenericMessageDto;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Log
@Path(Constants.DEF_PIPE_REST_API)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DefPipeResource {

    @Inject
    WorkflowService workflowService;

    @Inject
    WorkflowMapper workflowMapper;

    @GET
    @Path("/repo")
    @Operation(summary = "Get a specific workflow", description = "Get a specific workflow by user and workflow name")
    @Parameter(name = "user", description = "The user for which the workflows will be filtered", required = true)
    @Parameter(name = "workflowName", description = "The workflowName of the workflow which will be retrieved", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched Workflow",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = WorkflowDto.class)
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
    public Response getWorkflowByUserAndByName(@QueryParam("user") String user, @QueryParam("workflowName") String workflowName) {
        try{
            Response res = workflowService.getWorkflowByUserAndByName(user, workflowName);
            WorkflowDto workflowDto = res.readEntity(WorkflowDto.class);
            return Response.ok().entity(workflowDto).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @GET
    @Path("/repo/{user}")
    @Operation(summary = "Fetch user workflows", description = "Get a list with the user's workflows")
    @Parameter(name = "user", description = "The user for which the workflows will be retrieved", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Fetched User Workflows",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserWorkflowsDto.class)
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
    public Response getUserWorkflows(@PathParam("user") String user){
        try {
            Response res = workflowService.getUserWorkflows(user);
            UserWorkflowsDto userWorkflowsDto = res.readEntity(UserWorkflowsDto.class);
            return Response.ok().entity(userWorkflowsDto).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @POST
    @Path("/repo/{user}")
    @Operation(summary = "Store user workflow", description = "Create and store a user workflow")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Successfully Created User Workflow",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = WorkflowTemplateDto.class)
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
    public Response createUserWorkflow(@PathParam("user") String user, @RequestBody WorkflowTemplateDto workflowTemplateDto){
        try{
//            Response res = workflowService.createWorkflow(user, workflowTemplateDto);
            Workflow workflow = workflowMapper.toWorkflow(workflowTemplateDto);
            workflowService.store(workflow);
            return Response.ok(workflowTemplateDto).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/repo/{user}/{id}")
    @Operation(summary = "Delete a specific workflow", description = "Delete a specific workflow by user and by id")
    @Parameter(name = "user", description = "The user for which the workflows will be filtered", required = true)
    @Parameter(name = "id", description = "The id of the workflow which will be deleted", required = true)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Deleted Workflow",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DefPipeRestResponseDto.class)
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
    public Response deleteWorkflow(@PathParam("user") String user, @PathParam("id") String id){
        try{
            Response res = workflowService.deleteWorkflowByUserAndById(user, id);
            DefPipeRestResponseDto defPipeRestResponseDto = res.readEntity(DefPipeRestResponseDto.class);
            return Response.ok().entity(defPipeRestResponseDto).build();
        } catch (Exception e) {
            return Response.serverError().entity(new GenericMessageDto(e.getMessage())).build();
        }
    }

}
