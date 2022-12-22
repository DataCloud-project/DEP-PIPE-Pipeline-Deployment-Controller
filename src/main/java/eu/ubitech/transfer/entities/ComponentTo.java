package eu.ubitech.transfer.entities;

import eu.ubitech.transfer.enums.CapabilityAdd;
import eu.ubitech.transfer.enums.CapabilityDrop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentTo implements Serializable {

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
    private SortedSet<LabelTo> labels = new TreeSet<LabelTo>();
    private SortedSet<PluginTo> plugins = new TreeSet<PluginTo>();
    private Boolean publicComponent;
    private Boolean networkModeHost = false;
    private Boolean privilege = false;
    private String hostname;
    private String sharedMemorySize;
    private String command;
    private List<CapabilityDrop> capabilityDrops;
    private List<CapabilityAdd> capabilityAdds;
    private String ulimitMemlockSoft;
    private String ulimitMemlockHard;
    private String dockerExecutionUser;
    private UserTo user;
    private OrganizationTo organization;
    private Date dateCreated;
    private Date lastModified;

    public enum CapabilityDrop {
        //The lists Linux capability options which are allowed by default and can be dropped

        SETPCAP("SETPCAP"),
        MKNOD("MKNOD"),
        AUDIT_WRITE("AUDIT_WRITE"),
        CHOWN("CHOWN"),
        NET_RAW("NET_RAW"),
        DAC_OVERRIDE("DAC_OVERRIDE"),
        FOWNER("FOWNER"),
        FSETID("FSETID"),
        KILL("KILL"),
        SETGID("SETGID"),
        SETUID("SETUID"),
        NET_BIND_SERVICE("NET_BIND_SERVICE"),
        SYS_CHROOT("SYS_CHROOT"),
        SETFCAP("SETFCAP"),
        ALL("ALL");

        private String friendlyName;

        CapabilityDrop(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public String getFriendlyName() {
            return friendlyName;
        }
    }

    //TODO move to another place
    public enum CapabilityAdd {
        //The list Linux capability options which are not granted by default and may be added.

        SYS_MODULE("SYS_MODULE"),
        SYS_RAWIO("SYS_RAWIO"),
        SYS_PACCT("SYS_PACCT"),
        SYS_ADMIN("SYS_ADMIN"),
        SYS_NICE("SYS_NICE"),
        SYS_RESOURCE("SYS_RESOURCE"),
        SYS_TIME("SYS_TIME"),
        SYS_TTY_CONFIG("SYS_TTY_CONFIG"),
        AUDIT_CONTROL("AUDIT_CONTROL"),
        MAC_ADMIN("MAC_ADMIN"),
        MAC_OVERRIDE("MAC_OVERRIDE"),
        NET_ADMIN("NET_ADMIN"),
        SYSLOG("SYSLOG"),
        DAC_READ_SEARCH("DAC_READ_SEARCH"),
        LINUX_IMMUTABLE("LINUX_IMMUTABLE"),
        NET_BROADCAST("NET_BROADCAST"),
        IPC_LOCK("IPC_LOCK"),
        IPC_OWNER("IPC_OWNER"),
        SYS_PTRACE("SYS_PTRACE"),
        SYS_BOOT("SYS_BOOT"),
        LEASE("LEASE"),
        WAKE_ALARM("WAKE_ALARM"),
        BLOCK_SUSPEND("BLOCK_SUSPEND"),
        ALL("ALL");

        private String friendlyName;

        CapabilityAdd(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public String getFriendlyName() {
            return friendlyName;
        }
    }
}
