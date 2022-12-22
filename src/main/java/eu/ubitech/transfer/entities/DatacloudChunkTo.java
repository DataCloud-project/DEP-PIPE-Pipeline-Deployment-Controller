package eu.ubitech.transfer.entities;

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
public class DatacloudChunkTo implements Serializable {
    private Long id;
    private String chunkName;
    private String hexID;
    private List<DatacloudStepTo> stepsList;
    private Boolean publicChunk = false;
    private UserTo user;
    private OrganizationTo organization;
    private Date dateCreated;
    private Date lastModified;
}
