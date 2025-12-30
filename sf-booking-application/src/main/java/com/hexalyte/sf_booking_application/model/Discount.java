package com.hexalyte.sf_booking_application.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "discounts") // plural for consistency
@Data
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountID")
    private Long discountId;

    @Column(name = "DiscountName", nullable = false)
    private String discountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "DiscountType", nullable = false)
    private DiscountType discountType;

    @Column(name = "Value", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal value = BigDecimal.ZERO;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
}
