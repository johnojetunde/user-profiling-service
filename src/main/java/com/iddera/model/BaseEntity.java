package com.iddera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iddera.enums.Status;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @CreatedBy
    String createdBy;
    @LastModifiedBy
    String updatedBy;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 31)
    @ColumnDefault("'ACTIVE'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status recordStatus = Status.ACTIVE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if(id == null) return super.equals(o);

        BaseEntity baseModel = (BaseEntity) o;

        return new EqualsBuilder()
                .append(id, baseModel.id)
                .append(createdAt, baseModel.createdAt)
                .append(updatedAt, baseModel.updatedAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        if (id == null) return super.hashCode();

        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(createdAt)
                .append(updatedAt)
                .toHashCode();
    }
}
