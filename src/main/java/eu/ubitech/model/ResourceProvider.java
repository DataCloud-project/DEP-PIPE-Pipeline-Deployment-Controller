package eu.ubitech.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceProvider {
    private String name;
    private String provider;
    private String providerLocation;
    private String mappingLocation;
}
