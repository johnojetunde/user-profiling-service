package com.iddera.model;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

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