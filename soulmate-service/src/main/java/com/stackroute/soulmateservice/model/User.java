package com.stackroute.soulmateservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author Siva
 * @Date 11/11/2021 3:05 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @NotNull
    private String email;

    @NotNull
    private String password;
}
