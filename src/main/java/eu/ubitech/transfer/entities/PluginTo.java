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
@NoArgsConstructor
@AllArgsConstructor
public class PluginTo implements Serializable, Comparable<PluginTo> {

    private Long pluginID;
    private String name;
    private String moduleName;
    private String downloadURL;
    private String pluginType;
    private String port;
    private String endpoint;
    private Boolean publicPlugin;
    private Boolean immutablePlugin;
    private Boolean defaultPlugin;
    private UserTo user;
    private OrganizationTo organization;
    private Date dateCreated;
    private Date lastModified;
    private List<MetricTo> metrics;
    private Integer metricsCounter;

    @Override
    public int compareTo(PluginTo pluginTo) {
        return (int)(this.pluginID - pluginTo.getPluginID());

    }

}
