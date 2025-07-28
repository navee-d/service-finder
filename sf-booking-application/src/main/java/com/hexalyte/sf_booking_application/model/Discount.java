package com.hexalyte.sf_booking_application.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Discount")
@Data
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountID")
    private Long discountId;

    @Column(name = "DiscountName", nullable = false)
    private String discountName;

    @Column(name = "DiscountType",nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "value",columnDefinition = "DECIMAL(10,2)",nullable = false)
    private Double value;
}
