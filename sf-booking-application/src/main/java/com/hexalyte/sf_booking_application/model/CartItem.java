package com.hexalyte.sf_booking_application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_booking_application.model.deserializer.DiscountDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cartitems")
@Data
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    private Long cartItemId;

    @Column(name = "ServiceProviderID", nullable = false)
    private Long serviceProviderId;

    @Column(name = "ServiceID", nullable = false)
    private Long serviceId;  // changed from int to Long

    @Column(name = "Quantity", nullable = false)
    @ColumnDefault("1")
    @Positive(message = "Quantity cannot be negative or zero")
    private int quantity;

    @Column(name = "GrossPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal grossPrice = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "DiscountID")
    @JsonDeserialize(converter = DiscountDeserializer.class)
    private Discount discount;

    @Column(name = "NetPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal netPrice = BigDecimal.ZERO;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "CartID", nullable = false)
    @JsonBackReference("cartItems")
    private Cart cart;
}
