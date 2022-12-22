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
public class InterfaceTo implements Serializable {

    private Long interfaceID;
    private String name;
    private String port;
    private String vna;
    private String interfaceType;
    private String transmissionProtocol;
    private Date dateCreated;
    private Date lastModified;

}
