package com.hexalyte.sf_booking_application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long cartId;

    @Column(name = "UserID", nullable = false)
    private UUID userId;

    @Column(name = "total", columnDefinition = "DECIMAL(10,2)", nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonManagedReference("cartItems")
    private List<CartItem> cartItems;
}
