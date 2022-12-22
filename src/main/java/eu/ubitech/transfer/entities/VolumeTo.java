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
public class VolumeTo implements Serializable {

    private Long volumeID;
    private String dockerPath;
    private Boolean isFile = false;
    private Date dateCreated;
    private Date lastModified;

}
