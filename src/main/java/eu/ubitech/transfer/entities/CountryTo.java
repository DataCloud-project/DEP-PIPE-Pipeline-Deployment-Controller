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
public class CountryTo implements Serializable {

    private Long id;
    private String name;
    private String alpha2;
    private String alpha3;
    private String countryCode;
    private String iso3166;
    private String region;
    private String subRegion;
    private String regionCode;
    private String subRegionCode;
    private Date dateCreated;

}
