package com.hexalyte.sf_booking_application.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cartitems")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    private Long cartItemId;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @Column(name = "ServiceID")
    private Long serviceId;

    @Column(name = "Quantity", nullable = false)
    @ColumnDefault("1")
    private int quantity;

    @Column(name = "GrossPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private int grossPrice;

    @ManyToOne
    @JoinColumn(name = "DiscountID")
    private Discount discountId;

    @Column(name = "NetPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private int netPrice;

    @Column(name = "Description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "CreatedAt",columnDefinition = "TIMESTAMP",updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt",columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "CartID")
    private Cart cart;
}
