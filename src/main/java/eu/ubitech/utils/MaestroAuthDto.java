package eu.ubitech.utils;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaestroAuthDto implements Serializable {

    private String username;
    private String password;

}
