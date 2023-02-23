package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.entities.DatacloudChunkTo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class DatacloudChunkService {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response getDatacloudChunk(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.getDatacloudChunk(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response createDatacloudChunk(DatacloudChunkTo datacloudChunkTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.createDatacloudChunk(authToken, datacloudChunkTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response updateDatacloudChunk(DatacloudChunkTo datacloudChunkTo) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.updateDatacloudChunk(authToken, datacloudChunkTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteDatacloudChunk(Long id) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.deleteDatacloudChunk(authToken, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
