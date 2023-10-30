package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.entities.DatacloudProviderTo;
import eu.ubitech.transfer.entities.DatacloudProviderTypeTo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudProviderTypeService {
    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response getDatacloudProviderType(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.getDatacloudProviderType(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response fetchDatacloudProviderTypes(int page, int size, String sort, String filters, DatacloudProviderTypeTo datacloudProviderTypeTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.fetchDatacloudProviderTypes(authToken, page, size, sort, filters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
