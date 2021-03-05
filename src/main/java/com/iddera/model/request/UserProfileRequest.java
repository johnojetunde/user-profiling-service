package com.iddera.model.request;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Builder
public class UserProfileRequest {
    private Long userId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Marital status can not be null or empty")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender can not be null of empty")
    private Gender gender;

    @NotNull(message = "Country Id can not be null.")
    private Long countryId;

    @NotNull(message = "State Id can not be null.")
    private Long stateId;

    @NotNull(message = "Lga Id can not be null.")
    private Long lgaId;
}
