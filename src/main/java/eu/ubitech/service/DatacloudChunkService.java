package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.DatacloudPipelineChunkTo;
import eu.ubitech.transfer.entities.DatacloudChunkTo;
import eu.ubitech.transfer.entities.DatacloudStepTo;
import eu.ubitech.transfer.entities.GraphLinkTo;
import eu.ubitech.transfer.entities.InterfaceTo;
import eu.ubitech.utils.RandomHexIDGenerator;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
            datacloudChunkTo = renameInterfaces(datacloudChunkTo);
            return maestroRestClient.createDatacloudChunk(authToken, datacloudChunkTo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void createDatacloudPipelineChunk(DatacloudPipelineChunkTo datacloudPipelineChunkTo) {
        try {
            if (datacloudPipelineChunkTo != null && datacloudPipelineChunkTo.getChunksList() != null && !datacloudPipelineChunkTo.getChunksList().isEmpty()) {
                List<DatacloudChunkTo> chunksList = datacloudPipelineChunkTo.getChunksList();
                for (DatacloudChunkTo chunk : chunksList) {
                    createDatacloudChunk(chunk);
                }
            } else {
                throw new Exception("Datacloud pipeline chunk provided is either null or contains a null chunk list");
            }
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

    public DatacloudChunkTo renameInterfaces(DatacloudChunkTo datacloudChunkTo) {

        try {
            if (datacloudChunkTo != null && datacloudChunkTo.getStepsList() != null && !datacloudChunkTo.getStepsList().isEmpty()) {
                List<DatacloudStepTo> stepList = datacloudChunkTo.getStepsList();
                String hexID = RandomHexIDGenerator.generateRandomHexID(5);

                for (DatacloudStepTo step : stepList) {

                    if (step != null) {

                        if (step.getExposedInterfaces() != null && !step.getExposedInterfaces().isEmpty()) {
                            List<InterfaceTo> stepExposedInterfaces = step.getExposedInterfaces();
                            for (InterfaceTo exposedInterface : stepExposedInterfaces) {
                                exposedInterface.setName(exposedInterface.getName() + hexID);
                            }
                        }

                        if (step.getRequiredInterfaces() != null && !step.getRequiredInterfaces().isEmpty()) {
                            List<GraphLinkTo> stepRequiredInterfacesInterfaces = step.getRequiredInterfaces();
                            for (GraphLinkTo requiredInterface : stepRequiredInterfacesInterfaces) {
                                if (requiredInterface.getInterfaceObj() != null) {
                                    InterfaceTo requiredInterfaceObj = requiredInterface.getInterfaceObj();
                                    requiredInterfaceObj.setName(requiredInterfaceObj.getName() + hexID);
                                }
                            }
                        }
                    }
                }
                return datacloudChunkTo;

            } else {
                throw new Exception("Datacloud chunk provided is either null or contains a null step list");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
