package com.iddera.userprofile.api.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@NoArgsConstructor
@Data
public class LocationModel {
    @NotNull(message = "Country Id can not be null.")
    private Long countryId;
    @NotNull(message = "State Id can not be null.")
    private Long stateId;
    @NotNull(message = "Lga Id can not be null.")
    private Long lgaId;
}
