package eu.ubitech.service;

import eu.ubitech.clients.MaestroRestClient;
import eu.ubitech.constants.Constants;
import eu.ubitech.utils.MaestroAuthDto;
import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class MaestroAuthService {

    @Inject
    @RestClient
    MaestroRestClient maestroRestClient;

    @CacheResult(cacheName = "maestro-token-cache")
    public String maestroAuthenticate(MaestroAuthDto maestroAuthDto) throws Exception {
        String authToken;
        Pattern authTokenPattern = Pattern.compile(Constants.AUTH_TOKEN_REGEX);
        String authTokenCookie = maestroRestClient.maestroAuthenticate(maestroAuthDto)
                .getCookies()
                .get(Constants.AUTH_TOKEN_COOKIE).toString();
        Matcher m = authTokenPattern.matcher(authTokenCookie);

        if (m.find()) {
            authToken = m.group();
        } else {
            throw new Exception("Authentication Token missing from cookie");
        }
        return authToken;
    }

}
