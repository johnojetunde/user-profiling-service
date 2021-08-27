package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.location.model.CountryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    public static Country fromModel(CountryModel countryModel) {
        return Country.builder()
                .id(countryModel.getId())
                .name(countryModel.getName())
                .code(countryModel.getCode())
                .build();
    }
}
