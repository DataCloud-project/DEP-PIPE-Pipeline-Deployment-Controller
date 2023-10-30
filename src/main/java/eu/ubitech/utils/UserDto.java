package eu.ubitech.utils;

import eu.ubitech.transfer.entities.CountryTo;
import eu.ubitech.transfer.entities.OrganizationTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String verifyPassword;

    private CountryTo country = new CountryTo("Greece");
    private String role = "USER";
    private Boolean notificationWebEnabled;
    private Boolean notificationEmailEnabled;
    private String newPassword;
    private String currentPassword;
    private Boolean firstLogin = true;
    private Date dateCreated;
    private Boolean allowEdit;
    private Boolean allowDelete;
    private OrganizationTO organization = new OrganizationTO(Integer.toUnsignedLong(7), "SANDBOX");
    private Boolean enabled = false;

}
