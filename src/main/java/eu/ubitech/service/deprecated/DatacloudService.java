package eu.ubitech.service.deprecated;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.constants.Constants;
import eu.ubitech.service.deprecated.DatacloudInterface;
import eu.ubitech.transfer.entities.DatacloudStepTo;
import eu.ubitech.utils.MaestroAuthDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class DatacloudService implements DatacloudInterface {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @Override
    public String maestroAuthenticate(MaestroAuthDto maestroAuthDto) throws Exception {

        String authToken;

        Pattern authTokenPattern = Pattern.compile(Constants.AUTH_TOKEN_REGEX);

        String authTokenCookie = maestroRestClient.maestroAuthenticate(maestroAuthDto).getCookies().get(Constants.AUTH_TOKEN_COOKIE).toString();

        Matcher m = authTokenPattern.matcher(authTokenCookie);

        if (m.find()) {
            authToken = m.group();
        } else {
            throw new Exception("Authentication Token missing from cookie");
        }

        return authToken;
    }


    //TODO under construction
    @Override
    public Response createStep(String authToken, DatacloudStepTo datacloudStepTo) {
        return null;
    }
}
