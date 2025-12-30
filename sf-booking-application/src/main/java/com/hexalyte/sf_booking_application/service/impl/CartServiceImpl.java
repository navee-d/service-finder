package com.hexalyte.sf_booking_application.service.impl;

import com.hexalyte.sf_booking_application.model.Cart;
import com.hexalyte.sf_booking_application.model.CartItem;
import com.hexalyte.sf_booking_application.repository.CartRepository;
import com.hexalyte.sf_booking_application.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    public CartServiceImpl(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cart> getCarts() {
        return repository.findAll();
    }

    @Override
    public Optional<Cart> getCartByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Optional<Cart> addCart(Cart cart) {
        return Optional.of(repository.save(cart));
    }

    // ⭐ MODIFIED METHOD
    @Override
    public Optional<Cart> addCartItemToCart(Long cartId, CartItem cartItem) {
        return repository.findById(cartId).map(cart -> {
            // ** This is the fix: **
            // You must set the parent (Cart) on the child (CartItem)
            cartItem.setCart(cart);

            // Now, add the child to the parent's list
            cart.getCartItems().add(cartItem);

            // Saving the parent (Cart) will now cascade and save the
            // CartItem with the correct CartID.
            return repository.save(cart);
        });
    }

    @Override
    public Optional<Cart> updateCart(Long cartId, Cart cart) {
        return repository.findById(cartId).map(existing -> {
            cart.setCartId(existing.getCartId()); // fixed
            return repository.save(cart);
        });
    }


    @Override
    public void deleteCart(Long cartId) {
        repository.deleteById(cartId);
    }
}