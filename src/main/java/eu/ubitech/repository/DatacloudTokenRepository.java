package eu.ubitech.repository;

import eu.ubitech.model.DatacloudToken;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DatacloudTokenRepository implements PanacheMongoRepository<DatacloudToken> {

    public DatacloudToken getDatacloudTokenByUsername(String username){
        return find("username", username).firstResult();
    }

    public void storeDatacloudToken(DatacloudToken datacloudToken) {
        persist(datacloudToken);
    }
}
