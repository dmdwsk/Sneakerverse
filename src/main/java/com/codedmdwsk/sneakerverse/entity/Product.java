package com.codedmdwsk.sneakerverse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_products_slug", columnNames = "slug")
        },
        indexes = {
                @Index(name = "ix_products_brand_id", columnList = "brand_id"),
                @Index(name = "ix_products_status", columnList = "status"),
                @Index(name = "ix_products_slug", columnList = "slug")
        }
)
public class Product {

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.Set<ProductVariant> variants = new java.util.HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_products_brand"))
    private Brand brand;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /**
     * Human-friendly unique string for URLs: "nike-air-jordan-1-high".
     */
    @Column(name = "slug", nullable = false, length = 200)
    private String slug;

    @Column(name = "description", nullable = true, length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 16)
    private ProductGender gender;

    /**
     * Base price in UAH. Variant may override it.
     */
    @Column(name = "base_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private ProductStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
        if (status == null) status = ProductStatus.DRAFT;
        if (gender == null) gender = ProductGender.UNISEX;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
