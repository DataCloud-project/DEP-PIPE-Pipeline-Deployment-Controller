package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.entities.DatacloudProviderTo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudProviderService {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response getDatacloudProvider(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.getDatacloudProvider(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response createDatacloudProvider(DatacloudProviderTo datacloudProviderTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.createDatacloudProvider(authToken, datacloudProviderTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response updateDatacloudProvider(DatacloudProviderTo datacloudProviderTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.updateDatacloudProvider(authToken, datacloudProviderTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteDatacloudProvider(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.deleteDatacloudProvider(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response changeDefaultStatus(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.changeDatacloudProviderDefaultStatus(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response fetchDatacloudProviders(int page, int size, String sort, String filters, DatacloudProviderTo datacloudProviderTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.fetchDatacloudProviders(authToken, page, size, sort, filters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response fetchDatacloudProvidersForDeployment(int page, int size, String sort, String filters, DatacloudProviderTo datacloudProviderTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.fetchDatacloudProvidersForDeployment(authToken, page, size, sort, filters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
