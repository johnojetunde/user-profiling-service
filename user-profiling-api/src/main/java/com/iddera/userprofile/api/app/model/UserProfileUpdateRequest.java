package com.iddera.userprofile.api.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iddera.commons.annotation.ValidEnum;
import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileUpdateRequest {
    @ValidEnum(message = "Invalid marital status value")
    private MaritalStatus maritalStatus;
    @ValidEnum(message = "Invalid gender value")
    private Gender gender;
    private Long lgaId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
