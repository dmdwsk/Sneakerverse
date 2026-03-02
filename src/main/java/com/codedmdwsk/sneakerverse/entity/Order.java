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
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_orders_order_number", columnNames = "order_number")
        },
        indexes = {
                @Index(name = "ix_orders_user_id", columnList = "user_id"),
                @Index(name = "ix_orders_status", columnList = "status"),
                @Index(name = "ix_orders_created_at", columnList = "created_at")
        }
)
public class Order {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.Set<OrderItem> items = new java.util.HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    /**
     * Human-friendly identifier, e.g. "SV-2026-0000123"
     */
    @Column(name = "order_number", nullable = false, length = 32)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_orders_user"))
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 24)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 8)
    private CurrencyCode currency;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Snapshot of shipping address at checkout time.
     * JSON string for MVP (later можно вынести в отдельную таблицу order_addresses).
     */
    @Column(name = "shipping_address_snapshot", nullable = false, length = 4000)
    private String shippingAddressSnapshot;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
        if (status == null) status = OrderStatus.NEW;
        if (currency == null) currency = CurrencyCode.UAH;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}