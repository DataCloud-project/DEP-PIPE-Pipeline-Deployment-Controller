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
public class OrganizationTO implements Serializable {
    private Long id;
    private String name;
    private String status;
    private Integer usersCounter;
    private Date dateCreated;
    private Date lastModified;
    private Boolean allowEdit;
    private Boolean allowDelete;

    public OrganizationTO(Long id) {
        this.id = id;
    }

    public OrganizationTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
