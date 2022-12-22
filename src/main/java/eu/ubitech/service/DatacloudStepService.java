package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.entities.DatacloudStepTo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudStepService {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    public Response getDatacloudStep(String authToken, Long id) {
        return maestroRestClient.getDatacloudStep(authToken, id);
    }

    public Response createDatacloudStep(String authToken, DatacloudStepTo datacloudStepTo) {
        return maestroRestClient.createDatacloudStep(authToken, datacloudStepTo);
    }

    public Response updateDatacloudStep(String authToken, DatacloudStepTo datacloudStepTo) {
        return maestroRestClient.updateDatacloudStep(authToken, datacloudStepTo);
    }

    public Response deleteDatacloudStep(String authToken, Long id) {
        return maestroRestClient.deleteDatacloudStep(authToken, id);
    }

}
