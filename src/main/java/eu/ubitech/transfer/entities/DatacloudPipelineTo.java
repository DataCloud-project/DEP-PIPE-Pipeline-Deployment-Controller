package eu.ubitech.transfer.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DatacloudPipelineTo implements Serializable {
    String pipelineName;
    String pipelineType;
    String chunkName;
    TerminationCheckTo terminationCheckTo;
    List<DatacloudStepTo> stepsList;
}
