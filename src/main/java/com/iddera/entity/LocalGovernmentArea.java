package com.iddera.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "local_government_area")
public class LocalGovernmentArea extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(targetEntity = State.class)
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;
}