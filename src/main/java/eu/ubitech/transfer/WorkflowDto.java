package eu.ubitech.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowDto {

    private WorkflowTemplateDto data;
    private String errorMessage;
    private boolean success;
}
