package com.iddera.userprofile.api.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
