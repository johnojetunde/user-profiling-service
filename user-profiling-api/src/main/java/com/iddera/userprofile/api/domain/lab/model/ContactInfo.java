package com.iddera.userprofile.api.domain.lab.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Data
@Embeddable
public class ContactInfo {
    @NotBlank
    private String lineOne;
    @NotBlank
    private String lineTwo;
    @NotBlank
    private String email;
}
