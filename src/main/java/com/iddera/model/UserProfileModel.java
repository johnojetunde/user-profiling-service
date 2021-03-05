package com.iddera.model;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class UserProfileModel {
    private Long userId;
    private String country;
    private String state;
    private Gender gender;
    private MaritalStatus maritalStatus;
}