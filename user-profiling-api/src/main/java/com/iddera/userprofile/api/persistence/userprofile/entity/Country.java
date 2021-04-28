package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.location.model.CountryModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "country")
public class Country extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false, unique = true)
    private String name;

    public CountryModel toModel() {
        return CountryModel.builder()
                .id(getId())
                .name(getName())
                .code(getCode())
                .build();
    }
}
