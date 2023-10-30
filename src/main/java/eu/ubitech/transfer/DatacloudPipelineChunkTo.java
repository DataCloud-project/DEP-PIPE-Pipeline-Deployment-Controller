package eu.ubitech.transfer;

import eu.ubitech.transfer.entities.*;
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
public class DatacloudPipelineChunkTo implements Serializable {
    private String pipelineName;
    String pipelineType;
    TerminationCheckTo terminationCheckTo;
    List<DatacloudChunkTo> chunksList;
}
