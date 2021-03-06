package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.lab.model.HospitalModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "hospital")
@NoArgsConstructor
public class Hospital extends BaseEntity {
    private String name;

    private String address;


    public HospitalModel toModel() {
        var hospitalModel = new HospitalModel();
        hospitalModel.setAddress(address);
        hospitalModel.setName(name);
        hospitalModel.setId(id);
        return hospitalModel;
    }
}
