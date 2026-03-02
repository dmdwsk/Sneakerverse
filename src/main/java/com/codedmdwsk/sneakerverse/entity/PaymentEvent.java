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
        name = "payment_events",
        indexes = {
                @Index(name = "ix_payment_events_payment_id", columnList = "payment_id"),
                @Index(name = "ix_payment_events_created_at", columnList = "created_at")
        }
)
public class PaymentEvent {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_events_payment"))
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 48)
    private PaymentEventType type;

    /**
     * Raw provider payload (webhook body) for audit/debug.
     */
    @Column(name = "payload", nullable = false, length = 10000)
    private String payload;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
