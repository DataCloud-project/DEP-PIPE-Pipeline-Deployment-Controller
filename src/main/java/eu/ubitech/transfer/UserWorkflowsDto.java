package eu.ubitech.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkflowsDto {
    private List<WorkflowTemplateDto> data;
    private String errorMessage;
    private boolean success;
}
