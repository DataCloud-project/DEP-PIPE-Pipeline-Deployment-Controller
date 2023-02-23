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

    @Inject
    MaestroAuthService maestroAuthService;

    public Response getDatacloudStep(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.getDatacloudStep(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response createDatacloudStep(DatacloudStepTo datacloudStepTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.createDatacloudStep(authToken, datacloudStepTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response updateDatacloudStep(DatacloudStepTo datacloudStepTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.updateDatacloudStep(authToken, datacloudStepTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteDatacloudStep(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.deleteDatacloudStep(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
