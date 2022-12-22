package eu.ubitech.transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetricTo implements Serializable {

    private Long metricID;
    private String name;
    private String friendlyName;
    private String unit;
    private Date dateCreated;
    private Date lastModified;

}
