package eu.ubitech.transfer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CapabilityDrop {

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

    private final String friendlyName;

}
