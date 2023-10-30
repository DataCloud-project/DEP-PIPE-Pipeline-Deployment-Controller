package eu.ubitech.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO do we need to encrypt the token on save?
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "datacloudtoken")
public class DatacloudToken extends PanacheMongoEntity {
//    private String _id;
    private String username;
    private String token;
    private String createdAt;
}
