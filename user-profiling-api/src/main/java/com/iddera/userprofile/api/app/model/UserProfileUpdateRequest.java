package com.iddera.userprofile.api.app.model;

import com.iddera.userprofile.api.domain.model.LocationModel;
import com.iddera.userprofile.api.domain.userprofile.enums.Gender;
import com.iddera.userprofile.api.domain.userprofile.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileUpdateRequest {
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Marital status can not be null or empty")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender can not be null of empty")
    private Gender gender;

    @NotNull(message = "Location can not be null")
    @Valid
    private LocationModel location;
}
