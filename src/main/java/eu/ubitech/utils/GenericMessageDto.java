package eu.ubitech.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenericMessageDto implements Serializable {

    private String message;

    public static final String STEP_FETCHED = "Datacloud step has been fetched successfully";
    public static final String STEP_CREATED = "Datacloud step has been created successfully";
    public static final String STEP_UPDATED = "Datacloud step has been updated successfully";
    public static final String STEP_DELETED = "Datacloud step has been deleted successfully";
    public static final String CHUNK_FETCHED = "Datacloud chunk has been fetched successfully";
    public static final String CHUNK_CREATED = "Datacloud chunk has been created successfully";
    public static final String CHUNK_UPDATED = "Datacloud chunk has been updated successfully";
    public static final String CHUNK_DELETED = "Datacloud chunk has been deleted successfully";
    public static final String PIPELINE_FETCHED = "Datacloud pipeline has been fetched successfully";
    public static final String PIPELINE_CREATED = "Datacloud pipeline has been created successfully";
    public static final String PIPELINE_UPDATED = "Datacloud pipeline has been updated successfully";
    public static final String PIPELINE_DELETED = "Datacloud pipeline has been deleted successfully";
    public static final String PIPELINE_DEPLOYMENT_REQUESTED = "Datacloud pipeline deployment has been requested successfully";
    public static final String PIPELINE_UNDEPLOYMENT_REQUESTED = "Datacloud pipeline undeployment has been requested successfully";
    public static final String PIPELINE_CANCELLATION_REQUESTED = "Datacloud pipeline deployment cancellation has been requested successfully";
    public static final String PROVIDER_FETCHED = "Datacloud provider has been fetched successfully";
    public static final String PROVIDER_CREATED = "Datacloud provider has been created successfully";
    public static final String PROVIDER_UPDATED = "Datacloud provider has been updated successfully";
    public static final String PROVIDER_DELETED = "Datacloud provider has been deleted successfully";
    public static final String PROVIDER_DEFAULT_STATUS_CHANGED = "Datacloud provider default status has changed successfully";
    public static final String PROVIDER_LIST_FETCHED = "Datacloud provider list has been fetched successfully";
    public static final String PROVIDER_LIST_FOR_DEPLOYMENT_FETCHED = "Datacloud provider list for deployment has been fetched successfully";
    public static final String PROVIDER_TYPE_FETCHED = "Datacloud provider has been fetched successfully";
    public static final String PROVIDER_TYPE_LIST_FETCHED = "Datacloud provider list has been fetched successfully";
    public static final String KEYCLOAK_USER_CREATED = "Datacloud keycloak user has been created successfully";
    public static final String DATACLOUD_USER_CREATED = "Datacloud DEP user has been created successfully";
    public static final String KEYCLOAK_AND_DATACLOUD_USER_CREATED = "Keycloak and Datacloud DEP user has been created successfully";
    public static final String STEP_NODE_INSTANCE_AFFINITY_CREATED = "Step node instance affinity created successfully ";
    public static final String DATACLOUD_TOKEN_STORED = "Datacloud token has been stored successfully ";
    public static final String DATACLOUD_TOKEN_RETRIEVED = "Datacloud token has been retrieved successfully";
}
