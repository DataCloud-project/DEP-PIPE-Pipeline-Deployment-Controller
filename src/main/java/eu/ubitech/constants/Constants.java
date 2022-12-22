package eu.ubitech.constants;

import java.io.Serializable;

public final class Constants implements Serializable {

    // DATACLOUD REST API
    public static final String REST_API = "/api/v1/datacloud";

    // DATACLOUD STEP REST API
    public static final String STEP_REST_API = "/api/v1/datacloud/step";

    // DATACLOUD CHUNK REST API
    public static final String CHUNK_REST_API = "/api/v1/datacloud/chunk";

    // DATACLOUD PIPELINE REST API
    public static final String PIPELINE_REST_API = "/api/v1/datacloud/pipeline";

    // DATACLOUD PIPELINE DEPLOYMENT REST API
    public static final String PIPELINE_DEPLOYMENT_REST_API = "/api/v1/datacloud/pipelinedeployment/{pipelineDeploymentID}/request";

    // AUTH TOKEN FIELD KEY
    public static String AUTH_TOKEN_COOKIE = "auth_token";

    // AUTH TOKEN FIELD VALUE EXTRACTION
    public static String AUTH_TOKEN_REGEX = "(?<=auth_token=).*?(?=;)";

}