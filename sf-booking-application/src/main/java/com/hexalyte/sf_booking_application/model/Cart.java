// src/main/java/com/hexalyte/sf_booking_application/model/Cart.java
package com.hexalyte.sf_booking_application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Cart")
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long cartId;

    // ⭐ FIX: Explicitly define UUID column for MySQL
    @Column(name = "UserID", nullable = false, columnDefinition = "binary(16)")
    private UUID userId;

    // One Cart has many CartItems
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cartItems")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "TotalPrice", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalPrice; // add this to replace setTotal() usage

    @Column(name = "CreatedAt", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}