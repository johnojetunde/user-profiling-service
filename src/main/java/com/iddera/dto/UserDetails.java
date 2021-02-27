package com.iddera.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @NotNull(message = "User id can not be null")
    private Long user_id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Valid
    @NotNull(message = "Location can not be null")
    private Location location;
}
