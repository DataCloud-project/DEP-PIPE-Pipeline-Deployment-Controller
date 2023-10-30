package eu.ubitech.transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderQuotaTo {
    private Long id;
    private Integer runningInstances;
    private Integer maxInstances;
    private Double instancesUtilization;
    private Integer usedVirtualCPUs;
    private Integer maxVirtualCPUs;
    private Double virtualCPUsUtilization;
    private Integer usedMemory;
    private Integer maxMemory;
    private Double memoryUtilization;
    private Integer usedFloatingIPs;
    private Integer claimedFloatingIPs;
    private Double floatingIPsConsumption;
    private Date dateCreated;
    private Date lastModified;
}
