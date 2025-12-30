package com.hexalyte.sf_booking_application.service;

import com.hexalyte.sf_booking_application.model.Cart;
import com.hexalyte.sf_booking_application.model.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartService {
    List<Cart> getCarts();
    Optional<Cart> getCartByUserId(UUID userId);
    Optional<Cart> addCart(Cart cart);
    Optional<Cart> addCartItemToCart(Long cartId, CartItem cartItem);
    Optional<Cart> updateCart(Long cartId, Cart cart);
    void deleteCart(Long cartId);
}
