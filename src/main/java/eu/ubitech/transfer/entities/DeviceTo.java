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
public class DeviceTo implements Serializable {

    private Long deviceID;
    private String key;
    private String value;
    private Date dateCreated;
    private Date lastModified;

}
