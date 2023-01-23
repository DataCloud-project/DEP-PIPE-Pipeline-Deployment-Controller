package eu.ubitech.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.ubitech.model.ResourceProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowTemplateDto {

    private String id;
    private String _id;
    private String name;
    private String description;
    private String category;
    private String createdAt;
    private String modifiedAt;
    private String sourceTemplateId;
    @JsonIgnore
    private Object canvasTemplate;
    private List<ResourceProvider> resourceProviders;
    @JsonProperty("public")
    private boolean isPublic;

}
