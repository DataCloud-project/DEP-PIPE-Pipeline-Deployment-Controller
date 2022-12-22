package eu.ubitech.transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequirementTo implements Serializable {

    private Long requirementID;
    private Integer vCPUs;
    private Integer ram;
    private Integer storage;
    private String hypervisorType;
    private Boolean gpuRequired;
    private Date dateCreated;
    private Date lastModified;

    public Integer getvCPUs() {
        return vCPUs;
    }

    public void setvCPUs(Integer vCPUs) {
        this.vCPUs = vCPUs;
    }
}
