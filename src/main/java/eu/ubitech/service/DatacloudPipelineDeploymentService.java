package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudPipelineDeploymentService {
    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response requestDatacloudPipelineDeployment(Long pipelineDeploymentID) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.requestDeployment(authToken, pipelineDeploymentID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response requestDatacloudPipelineUndeployment(Long pipelineDeploymentID) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.requestUndeployment(authToken, pipelineDeploymentID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response requestDatacloudPipelineCancellation(Long pipelineDeploymentID) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.requestCancellation(authToken, pipelineDeploymentID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
