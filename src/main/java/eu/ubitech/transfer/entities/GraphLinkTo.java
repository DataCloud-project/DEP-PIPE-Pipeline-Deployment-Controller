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
public class GraphLinkTo implements Serializable {

    private Long graphLinkID;
    private String friendlyName;
    private InterfaceTo interfaceObj;
    private Date dateCreated;
    private Date lastModified;
}
