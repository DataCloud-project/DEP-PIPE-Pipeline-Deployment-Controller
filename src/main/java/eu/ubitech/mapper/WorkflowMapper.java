package eu.ubitech.mapper;

import eu.ubitech.model.Workflow;
import eu.ubitech.transfer.WorkflowTemplateDto;
import org.bson.types.ObjectId;
import org.mapstruct.*;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface WorkflowMapper {

    // Map to workflow entity
    @Mapping(target = "id", ignore = true)
    Workflow toWorkflow (WorkflowTemplateDto WorkflowTemplateDto);

    // Map to workflow DTO
    @Mapping(target = "id", source = "id", qualifiedByName = "ObjectIdToStringId")
    WorkflowTemplateDto toWorkflowTemplateDto (Workflow Workflow);

    // Mapper method for mongo ID
    @Named("ObjectIdToStringId")
    static String ObjectIdToStringId(ObjectId id) {
        return id.toHexString();
    }

}
