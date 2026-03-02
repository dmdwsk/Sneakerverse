package com.codedmdwsk.sneakerverse.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "order_items",
        indexes = {
                @Index(name = "ix_order_items_order_id", columnList = "order_id"),
                @Index(name = "ix_order_items_variant_id", columnList = "variant_id")
        }
)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_items_order"))
    private Order order;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "variant_id",
            foreignKey = @ForeignKey(name = "fk_order_items_variant"))
    private ProductVariant variant;

    @Column(name = "product_name_snapshot", nullable = false, length = 200)
    private String productNameSnapshot;

    @Column(name = "size_snapshot", nullable = false, length = 16)
    private String sizeSnapshot;

    @Column(name = "color_snapshot", nullable = false, length = 40)
    private String colorSnapshot;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "line_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal lineTotal;
}
