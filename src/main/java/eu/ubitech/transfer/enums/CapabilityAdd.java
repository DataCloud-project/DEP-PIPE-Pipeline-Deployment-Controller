package eu.ubitech.transfer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CapabilityAdd {

    //The lists Linux capability options which are allowed by default and can be dropped

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

    private final String friendlyName;

}
