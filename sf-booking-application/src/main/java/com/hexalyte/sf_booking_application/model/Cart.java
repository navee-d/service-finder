package com.hexalyte.sf_booking_application.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long cartId;

    @Column(name = "UserID",nullable = false)
    private Long userId;

    @Column(name = "total",columnDefinition = "DECIMAL(10,2)")
    private Double total;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}
