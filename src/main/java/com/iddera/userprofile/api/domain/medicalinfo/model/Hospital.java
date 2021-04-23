package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "hospital")
public class Hospital extends BaseEntity {
    private String name;

    private String address;
}
