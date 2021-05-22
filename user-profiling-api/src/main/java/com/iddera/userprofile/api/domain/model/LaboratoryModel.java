package com.iddera.userprofile.api.domain.model;
import com.iddera.userprofile.api.persistence.medicals.entity.Laboratory;
import lombok.Data;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
public class LaboratoryModel {
    private Long id;

    @NotBlank(message = "Laboratory name cannot be empty or null")
    private String name;

    @NotBlank(message = "Laboratory address cannot be empty or null")
    private String address;

    private String specialisation;

    @Valid
    @Embedded
    private WorkingHour workingHour;

    private String additionalInfo;

    @Valid
    @Embedded
    private ContactInfo contactInfo;


    public Laboratory toEntity() {
        var entity = new Laboratory()
                .setName(name)
                .setAddress(address)
                .setSpecialisation(specialisation)
                .setWorkingHour(workingHour)
                .setAdditionalInfo(additionalInfo)
                .setContactInfo(contactInfo);
        entity.setId(id);
        return entity;
    }
}
