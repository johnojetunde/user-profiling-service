package com.iddera.userprofile.api.domain.userprofile.model;

import com.iddera.userprofile.api.domain.userprofile.enums.Gender;
import com.iddera.userprofile.api.domain.userprofile.enums.MaritalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class UserProfileModel {
    private Long userId;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Long countryId;
    private Long stateId;
    private Long lgaId;
}