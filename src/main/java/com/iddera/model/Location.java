package com.iddera.model;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Data
@Embeddable
public class Location {
    @NotBlank(message = "Country can not be null or empty")
    @Column(nullable = false, length = 50, name = "country")
    private String country;

    @NotBlank(message = "State can not be null or empty")
    @Column(nullable = false, length = 50, name = "state")
    private String state;
}
