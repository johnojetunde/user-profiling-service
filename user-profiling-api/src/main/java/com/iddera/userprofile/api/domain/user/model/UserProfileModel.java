package com.iddera.userprofile.api.domain.user.model;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.domain.location.model.LocalGovernmentAreaModel;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class UserProfileModel {
    private Long userId;
    private Gender gender;
    private LocalDate dateOfBirth;
    private MaritalStatus maritalStatus;
    private LocalGovernmentAreaModel lga;
}