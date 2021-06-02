package com.iddera.userprofile.api.domain.lab.model;

import com.iddera.userprofile.api.persistence.medicals.entity.Hospital;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HospitalModel {
    private Long id;

    @NotBlank(message = "Hospital name cannot be empty or null")
    private String name;

    @NotBlank(message = "Hospital address cannot be empty or null")
    private String address;

    public HospitalModel() {
    }

    public Hospital toEntity() {
        var entity = new Hospital()
                .setName(name)
                .setAddress(address);

        entity.setId(id);
        return entity;
    }
}
