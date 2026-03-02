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
        name = "product_variants",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_variants_sku", columnNames = "sku")
        },
        indexes = {
                @Index(name = "ix_product_variants_product_id", columnList = "product_id"),
                @Index(name = "ix_product_variants_status", columnList = "status")
        }
)
public class ProductVariant {

    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inventory inventory;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_variants_product"))
    private Product product;

    /**
     * Unique SKU, e.g. "AJ1-RED-42".
     */
    @Column(name = "sku", nullable = false, length = 64)
    private String sku;

    /**
     * EU size as string to support "42", "42.5", "43 1/3", etc.
     */
    @Column(name = "size", nullable = false, length = 16)
    private String size;

    @Column(name = "color", nullable = false, length = 40)
    private String color;

    /**
     * Optional override price in UAH (otherwise use Product.basePrice).
     */
    @Column(name = "price", nullable = true, precision = 19, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VariantStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
        if (status == null) status = VariantStatus.ACTIVE;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}