package eu.ubitech.service.deprecated;

import eu.ubitech.transfer.entities.DatacloudStepTo;
import eu.ubitech.utils.MaestroAuthDto;

import javax.ws.rs.core.Response;

public interface DatacloudInterface {

    String maestroAuthenticate(MaestroAuthDto maestroAuthDto) throws Exception;

    Response createStep(String authToken, DatacloudStepTo datacloudStepTo);

}
