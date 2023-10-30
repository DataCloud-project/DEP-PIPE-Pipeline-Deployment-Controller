package eu.ubitech.transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DatacloudProviderTo {
    private Long providerID;
    private String name;
    private ProviderTypeTo providerType;
    private Boolean defaultProvider;
    private Integer regionsCounter;
    private List<RegionTo> regions;
    private Date dateCreated;
    private Date lastModified;
    private ProviderQuotaTo providerQuota;
    private String organization;
    private Boolean allowEdit;
    private Boolean allowDelete;
}
