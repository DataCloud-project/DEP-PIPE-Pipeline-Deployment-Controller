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
public class EnvironmentalVariableTo implements Serializable {

    private Long environmentalVariableID;
    private String key;
    private String value;
    private Date dateCreated;
    private Date lastModified;

}
