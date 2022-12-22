package eu.ubitech.transfer.entities;

import eu.ubitech.transfer.enums.CapabilityAdd;
import eu.ubitech.transfer.enums.CapabilityDrop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobTo extends ComponentTo {

    private Long id;
    private String name;
    private String hexID;
    private String architecture;
    private String elasticityControllerMode;
    private String dockerImage;
    private String dockerRegistry;
    private Boolean dockerCredentialsUsing = false;
    private Boolean dockerCustomRegistry = false;
    private String dockerUsername;
    private String dockerPassword;
    private List<InterfaceTo> exposedInterfaces;
    private List<GraphLinkTo> requiredInterfaces;
    private RequirementTo requirement;
    private HealthCheckTo healthCheck;
    private List<EnvironmentalVariableTo> environmentalVariables;
    private List<DeviceTo> devices;
    private String elasticityController;
    private List<VolumeTo> volumes;
    private SortedSet<LabelTo> labels = new TreeSet<>();
    private SortedSet<PluginTo> plugins = new TreeSet<>();
    private Boolean publicComponent;
    private Boolean networkModeHost = false;
    private Boolean privilege = false;
    private String hostname;
    private String sharedMemorySize;
    private String command;
    private Collection<CapabilityDrop> capabilityDrops;
    private Collection<CapabilityAdd> capabilityAdds;
    private String ulimitMemlockSoft;
    private String ulimitMemlockHard;
    private String dockerExecutionUser;
    private UserTo user;
    private OrganizationTo organization;
    private Date dateCreated;
    private Date lastModified;
    private String resource;
    private TerminationCheckTo terminationCheckTo;
    private Integer numWorkers;

    public List<CapabilityDrop> getCapabilityDrops() {
        return (List<CapabilityDrop>) capabilityDrops;
    }

    public void setCapabilityDrops(Collection<CapabilityDrop> capabilityDrops) {
        this.capabilityDrops = capabilityDrops;
    }

    public List<CapabilityAdd> getCapabilityAdds() {
        return (List<CapabilityAdd>) capabilityAdds;
    }

    public void setCapabilityAdds(Collection<CapabilityAdd> capabilityAdds) {
        this.capabilityAdds = capabilityAdds;
    }

}
