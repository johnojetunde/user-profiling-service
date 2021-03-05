package com.iddera.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iddera.entity.LocalGovernmentArea;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class LocalGovernmentAreaDto {
    private long id;

    @NotBlank(message = "lga name can not be null or empty")
    private String name;

    @NotBlank(message = "lga code can not be null or empty")
    private String code;

    private String state;

        public static LocalGovernmentAreaDto convertToDto(LocalGovernmentArea localGovernmentArea) {
            return LocalGovernmentAreaDto.builder()
                    .id(localGovernmentArea.getId())
                    .name(localGovernmentArea.getName() != null ? localGovernmentArea.getName() : "")
                    .code(localGovernmentArea.getCode() != null ? localGovernmentArea.getCode() : "")
                    .state(localGovernmentArea.getState() != null? localGovernmentArea.getState().getName() : "")
                    .build();
        }

        public static Set<LocalGovernmentAreaDto> convertToDto(Set<LocalGovernmentArea> localGovernmentAreas) {
            Set<LocalGovernmentAreaDto> lgas = new HashSet<>();
            for(LocalGovernmentArea lga : localGovernmentAreas) {
                lgas.add(LocalGovernmentAreaDto.builder()
                    .id(lga.getId())
                    .name(lga.getName() != null ? lga.getName() : "")
                    .code(lga.getCode() != null ? lga.getCode() : "")
                    .state(lga.getState() != null? lga.getState().getName() : "")
                    .build());
            }
            return lgas;
        }
    }
