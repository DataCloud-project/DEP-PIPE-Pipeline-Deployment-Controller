package eu.ubitech.service;

import eu.ubitech.clients.DefPipeRestClient;
import eu.ubitech.model.Workflow;
import eu.ubitech.repository.WorkflowRepository;
import eu.ubitech.transfer.WorkflowTemplateDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class WorkflowService {

    @Inject
    WorkflowRepository workflowRepository;

    @Inject
    @RestClient
    DefPipeRestClient defPipeRestClient;


    public Response getUserWorkflows(String user) {
        return defPipeRestClient.getUserWorkflows(user);
    }

    public Response getWorkflowByUserAndByName(String user, String workflowName) {
        return defPipeRestClient.getWorkflowByUserAndByName(user, workflowName);
    }

    public void store(Workflow workflow) {
        workflowRepository.storeWorkflow(workflow);
    }

    public Response createWorkflow(String user, WorkflowTemplateDto workflowTemplateDto) {
        return defPipeRestClient.createWorkflow(user, workflowTemplateDto);
    }
    public Response deleteWorkflowByUserAndById(String user, String id) {
        return defPipeRestClient.deleteWorkflowByUserAndById(user, id);
    }


}
