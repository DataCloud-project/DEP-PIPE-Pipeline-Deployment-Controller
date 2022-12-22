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

    public Response getDatacloudPipeline(String authToken, Long id) {
        return maestroRestClient.getDatacloudPipeline(authToken, id);
    }

    public Response createDatacloudPipeline(String authToken, DatacloudPipelineTo datacloudPipelineTo) {
        return maestroRestClient.createDatacloudPipeline(authToken, datacloudPipelineTo);
    }

    public Response updateDatacloudPipeline(String authToken, DatacloudPipelineTo datacloudPipelineTo) {
        return maestroRestClient.updateDatacloudPipeline(authToken, datacloudPipelineTo);
    }

    public Response deleteDatacloudPipeline(String authToken, Long id) {
        return maestroRestClient.deleteDatacloudPipeline(authToken, id);
    }
}