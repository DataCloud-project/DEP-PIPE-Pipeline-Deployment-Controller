package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.entities.DatacloudPipelineTo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudPipelineDeploymentService {
    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    public Response requestDatacloudPipelineDeployment(String authToken, Long pipelineDeploymentID) {
        return maestroRestClient.requestDeployment(authToken, pipelineDeploymentID);
    }

    public Response requestDatacloudPipelineUndeployment(String authToken, Long pipelineDeploymentID) {
        return maestroRestClient.requestUndeployment(authToken, pipelineDeploymentID);
    }

    public Response requestDatacloudPipelineCancellation(String authToken, Long pipelineDeploymentID) {
        return maestroRestClient.requestCancellation(authToken, pipelineDeploymentID);
    }
}
