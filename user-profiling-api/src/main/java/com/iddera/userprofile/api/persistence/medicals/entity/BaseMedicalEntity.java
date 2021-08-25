package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseMedicalEntity extends BaseEntity {
    String username;
}
