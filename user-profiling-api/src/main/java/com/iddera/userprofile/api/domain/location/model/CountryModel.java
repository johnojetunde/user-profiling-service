package com.iddera.userprofile.api.domain.location.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CountryModel {
    private long id;

    @NotBlank(message = "Country code can not be empty or null")
    private String code;

    @NotBlank(message = "Country name can not be empty or null")
    private String name;
}
