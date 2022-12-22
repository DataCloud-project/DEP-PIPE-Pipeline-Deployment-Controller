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

    public Response getDatacloudChunk(String authToken, Long id) {
        return maestroRestClient.getDatacloudChunk(authToken, id);
    }

    public Response createDatacloudChunk(String authToken, DatacloudChunkTo datacloudChunkTo) {
        return maestroRestClient.createDatacloudChunk(authToken, datacloudChunkTo);
    }

    public Response updateDatacloudChunk(String authToken, DatacloudChunkTo datacloudChunkTo) {
        return maestroRestClient.updateDatacloudChunk(authToken, datacloudChunkTo);
    }

    public Response deleteDatacloudChunk(String authToken, Long id) {
        return maestroRestClient.deleteDatacloudChunk(authToken, id);
    }

}
