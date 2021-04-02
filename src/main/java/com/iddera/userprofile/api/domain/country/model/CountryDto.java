package com.iddera.userprofile.api.domain.country.model;

import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CountryDto {
    private long id;

    @NotBlank(message = "Country code can not be empty or null")
    private String code;

    @NotBlank(message = "Country name can not be empty or null")
    private String name;

    public static CountryDto convertToDto(Country country){
        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName() != null ? country.getName() :"")
                .code(country.getCode()!= null ? country.getCode() : "")
                .build();
    }

}
