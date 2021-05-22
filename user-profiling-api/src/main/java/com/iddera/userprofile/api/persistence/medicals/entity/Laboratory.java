package com.iddera.userprofile.api.persistence.medicals.entity;
import com.iddera.userprofile.api.domain.model.ContactInfo;
import com.iddera.userprofile.api.domain.model.LaboratoryModel;
import com.iddera.userprofile.api.domain.model.WorkingHour;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "laboratory")
public class Laboratory extends BaseEntity {
    private String name;

    private String address;

    private String specialisation;

    @Valid
    private WorkingHour workingHour;

    private String additionalInfo;

    @Embedded
    private ContactInfo contactInfo;

    public LaboratoryModel toModel() {
        var laboratoryModel = new LaboratoryModel();
        laboratoryModel.setAddress(address);
        laboratoryModel.setName(name);
        laboratoryModel.setId(id);
        laboratoryModel.setSpecialisation(specialisation);
        laboratoryModel.setWorkingHour(workingHour);
        laboratoryModel.setAdditionalInfo(additionalInfo);
        laboratoryModel.setContactInfo(contactInfo);
        return laboratoryModel;
    }
}
