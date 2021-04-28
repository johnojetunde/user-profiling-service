package com.iddera.userprofile.api.domain.location.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class LocalGovernmentAreaModel {
    private long id;
    @NotBlank(message = "lga name can not be null or empty")
    private String name;
    @NotBlank(message = "lga code can not be null or empty")
    private String code;
    private StateModel state;
}
