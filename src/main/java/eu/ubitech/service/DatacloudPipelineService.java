package eu.ubitech.service;
import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.entities.DatacloudPipelineTo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudPipelineService {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response getDatacloudPipeline(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.getDatacloudPipeline(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response createDatacloudPipeline(DatacloudPipelineTo datacloudPipelineTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.createDatacloudPipeline(authToken, datacloudPipelineTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response updateDatacloudPipeline(DatacloudPipelineTo datacloudPipelineTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.updateDatacloudPipeline(authToken, datacloudPipelineTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteDatacloudPipeline(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.deleteDatacloudPipeline(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}