package com.iddera.userprofile.api.domain.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Data
@Embeddable
public class ContactInfo {
    @NotBlank(message = "can not be null or empty")
    private String lineOne;
    @NotBlank(message = "can not be null or empty")
    private String lineTwo;
    @NotBlank(message = "can not be null or empty")
    private String email;
}
