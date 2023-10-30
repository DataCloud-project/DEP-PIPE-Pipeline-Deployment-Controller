package eu.ubitech.service;

import eu.ubitech.model.DatacloudToken;
import eu.ubitech.repository.DatacloudTokenRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DatacloudTokenService {
    @Inject
    DatacloudTokenRepository datacloudTokenRepository;


    public DatacloudToken getDatacloudTokenByUsername(String username) throws Exception {
        return datacloudTokenRepository.getDatacloudTokenByUsername(username);
    }

    public void storeDatacloudToken(DatacloudToken datacloudToken) throws Exception {
        datacloudTokenRepository.storeDatacloudToken(datacloudToken);
    }

}
