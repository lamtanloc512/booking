package org.hrsgroup.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @JsonbTransient
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    @JsonbTransient
    @Column(name = "updated_at", insertable = false)
    protected LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now(ZoneId.of("UTC"));
        updatedAt = null;
    }

    @PreUpdate
    void onUpdate() {
        createdAt = LocalDateTime.now(ZoneId.of("UTC"));
        updatedAt = null;
    }
}
