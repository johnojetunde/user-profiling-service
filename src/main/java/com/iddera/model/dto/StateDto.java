package com.iddera.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iddera.entity.State;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class StateDto {
    private long id;

    @NotBlank(message = "state name can not be null or empty")
    private String name;

    @NotBlank(message = "state code can not be null or empty")
    private String code;

    private String country;

        public static StateDto convertToDto(State state) {
            return StateDto.builder()
                    .id(state.getId())
                    .name(state.getName() != null ? state.getName() : "")
                    .code(state.getCode() != null ? state.getCode() : "")
                    .country(state.getCountry() != null ? state.getCountry().getName() : "")
                    .build();
        }
    }
