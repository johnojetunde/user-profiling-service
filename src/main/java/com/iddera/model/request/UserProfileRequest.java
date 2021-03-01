package com.iddera.model.request;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class UserProfileRequest {
    private Long userId;

    @NotNull(message = "Marital status can not be null or empty")
    private MaritalStatus maritalStatus;

    @NotNull(message = "Gender can not be null of empty")
    private Gender gender;

    @NotNull(message = "Location can not be null")
    private Location location;
}
