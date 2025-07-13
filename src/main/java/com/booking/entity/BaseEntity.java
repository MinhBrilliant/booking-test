package com.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_at")
    public Timestamp createdAt;

    @CreatedBy
    @Column(name = "created_by")
    public String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    public Timestamp updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    public String updatedBy;
}
