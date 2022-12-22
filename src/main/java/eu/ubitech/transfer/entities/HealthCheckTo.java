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
public class HealthCheckTo implements Serializable {

    private Long healthCheckID;
    private String name;
    private String httpURL;
    private String args;
    private Long interval;
    private Date dateCreated;
    private Date lastModified;

}
