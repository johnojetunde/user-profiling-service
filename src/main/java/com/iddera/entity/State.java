package com.iddera.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "state")
public class State extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;
}