package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.transfer.StepNodeInstanceAffinityDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class MaestroKubernetesService {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Inject
    MaestroAuthService maestroAuthService;

    public Response getClusterLabels(Long providerId) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.fetchK8sLabelsByProvider(authToken, providerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response createStepNodeInstanceAffinity(Long chunkId, Long sniId, StepNodeInstanceAffinityDto stepNodeInstanceAffinityDto) {
        try {
            String authToken = maestroAuthService.maestroAuthenticate();
            return maestroRestClient.createCNIAffinity(authToken, chunkId, sniId, stepNodeInstanceAffinityDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}