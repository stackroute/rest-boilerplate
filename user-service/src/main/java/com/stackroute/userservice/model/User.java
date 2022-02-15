package com.stackroute.userservice.model;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author Siva
 * @Date 10/30/2021 3:00 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@Entity
public class User implements Serializable {
    @Id
    @NotNull
    private String email;

    @NotNull
    @Pattern(regexp="^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$",message="password must 8 characters, 2 UpperCase, 1 Special Character, 2 Numbers & 3 LowerCase")
    private String password;
}
