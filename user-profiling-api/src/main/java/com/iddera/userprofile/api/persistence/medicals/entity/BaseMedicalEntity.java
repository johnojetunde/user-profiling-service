package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseMedicalEntity extends BaseEntity {
    String username;
}
