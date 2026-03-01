package com.codedmdwsk.sneakerverse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "addresses",
        indexes = {
                @Index(name = "ix_addresses_user_id", columnList = "user_id"),
                @Index(name = "ix_addresses_user_type", columnList = "user_id,type")
        }
)
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_addresses_user"))
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 16)
    private AddressType type;

    @Column(name = "country", nullable = false, length = 64)
    private String country;   // для MVP можна завжди "Ukraine"

    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Column(name = "postal_code", nullable = true, length = 16)
    private String postalCode; // в Україні не завжди потрібно

    @Column(name = "street", nullable = false, length = 128)
    private String street;

    @Column(name = "building", nullable = false, length = 32)
    private String building;

    @Column(name = "apartment", nullable = true, length = 32)
    private String apartment;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
        if (type == null) type = AddressType.SHIPPING;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
