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
}
