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
public class UserTo implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private CountryTo country;
    private String role;
    private Boolean notificationWebEnabled;
    private Boolean notificationEmailEnabled;
    private Date dateCreated;
    private Boolean firstLogin;
    private Boolean enabled;
    private OrganizationTo organization;
    private String newPassword;
    private String verifyPassword;
    private String currentPassword;

}
