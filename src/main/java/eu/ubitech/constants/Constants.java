package eu.ubitech.constants;

import java.io.Serializable;

public final class Constants implements Serializable {

    // DATACLOUD REST API
    public static final String REST_API = "/dc/api/v1/datacloud";

    // DATACLOUD STEP REST API
    public static final String STEP_REST_API = "/dc/api/v1/datacloud/step";

    // DATACLOUD CHUNK REST API
    public static final String CHUNK_REST_API = "/dc/api/v1/datacloud/chunk";

    // DATACLOUD PIPELINE REST API
    public static final String PIPELINE_REST_API = "/dc/api/v1/datacloud/pipeline";

    // DATACLOUD PIPELINE DEPLOYMENT REST API
    public static final String PIPELINE_DEPLOYMENT_REST_API = "/api/v1/datacloud/pipelinedeployment/{pipelineDeploymentID}/request";

    // USER_REGISTRATION_REST_API
    public static final String USER_REGISTRATION_REST_API = "/dc/api/v1/datacloud/user/register";

    // USER AUTHENTICATION REST API

    public static final String USER_AUTH_REST_API = "dc/api/v1/datacloud/auth/";

    // DEF-PIPE REST API
    public static final String DEF_PIPE_REST_API = "/dc/api/v1/defpipe";
    public static final String PROVIDER_REST_API = "/dc/api/v1/datacloud/provider";
    public static final String PROVIDER_TYPE_REST_API = "/dc/api/v1/datacloud/providertype";

    // MAESTRO KUBERNETES REST API
    public static final String MAESTRO_KUBERNETES_REST_API = "/dc/api/v1/datacloud/kubernetes";

    // DATACLOUD TOKEN STORAGE REST API;
    public static final String TOKEN_STORAGE_REST_API = "dc/api/v1/datacloud/token";

    // AUTH TOKEN FIELD KEY
    public static String AUTH_TOKEN_COOKIE = "auth_token";

    // AUTH TOKEN FIELD VALUE EXTRACTION
    public static String AUTH_TOKEN_REGEX = "(?<=auth_token=).*?(?=;)";
}