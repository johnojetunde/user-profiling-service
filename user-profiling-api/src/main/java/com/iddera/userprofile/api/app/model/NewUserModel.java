package com.iddera.userprofile.api.app.model;

import com.iddera.commons.annotation.FieldMatch;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Password and confirm password does not match"
)
public class NewUserModel {
    @NotBlank(message = "Firstname is required")
    private String firstName;
    @NotBlank(message = "Lastname is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email supplied")
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotBlank
    @Size(min = 6)
    private String confirmPassword;
}
