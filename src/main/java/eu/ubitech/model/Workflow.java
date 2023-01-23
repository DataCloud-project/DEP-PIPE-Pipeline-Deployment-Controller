package eu.ubitech.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "workflow")
public class Workflow extends PanacheMongoEntity {

    private String _id;
    private String name;
    private String description;
    private String category;
    private String createdAt;
    private String modifiedAt;
    private String sourceTemplateId = null;
    private List<ResourceProvider> resourceProviders;
    private boolean isPublic;

}
