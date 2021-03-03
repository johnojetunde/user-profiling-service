package com.iddera.model.request;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Builder
public class UserProfileRequest {
    private String username;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Marital status can not be null or empty")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender can not be null of empty")
    private Gender gender;

    @NotNull(message = "Location can not be null")
    private Location location;
}
