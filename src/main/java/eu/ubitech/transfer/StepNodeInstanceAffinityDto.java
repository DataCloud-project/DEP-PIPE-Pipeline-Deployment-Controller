package eu.ubitech.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StepNodeInstanceAffinityDto implements Serializable {
    private Long applicationInstanceId;
    private Long componentNodeInstanceId;
    private List<String> affinityLabels;
    private Date dateCreated;
    private Date lastModified;
}