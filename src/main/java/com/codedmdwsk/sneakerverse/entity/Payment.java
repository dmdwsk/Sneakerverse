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
        name = "payments",
        indexes = {
                @Index(name = "ix_payments_order_id", columnList = "order_id"),
                @Index(name = "ix_payments_provider_payment_id", columnList = "provider_payment_id"),
                @Index(name = "ix_payments_status", columnList = "status")
        }
)
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    /**
     * Many attempts per order are possible (failed -> retry).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_order"))
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 24)
    private PaymentProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 24)
    private PaymentStatus status;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 8)
    private CurrencyCode currency; // UAH

    /**
     * Identifier from provider side (Stripe payment_intent id / PayU orderId etc.)
     */
    @Column(name = "provider_payment_id", nullable = true, length = 128)
    private String providerPaymentId;

    /**
     * Optional: URL for redirect-based flows (if provider requires redirect).
     */
    @Column(name = "checkout_url", nullable = true, length = 1000)
    private String checkoutUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
        if (status == null) status = PaymentStatus.INITIATED;
        if (currency == null) currency = CurrencyCode.UAH;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
