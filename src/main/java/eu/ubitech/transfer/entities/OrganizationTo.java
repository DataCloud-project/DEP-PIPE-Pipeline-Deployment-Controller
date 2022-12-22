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
public class OrganizationTo implements Serializable {

    private Long id;
    private String name;
    private String status;
    private List<UserTo> users;
    private Date dateCreated;
    private Date lastModified;

}
