package com.hexalyte.sf_booking_application.controller;

import com.hexalyte.sf_booking_application.model.Cart;
import com.hexalyte.sf_booking_application.model.CartItem;
import com.hexalyte.sf_booking_application.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(service.getCarts());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable UUID userId) {
        return ResponseEntity.of(service.getCartByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Cart> addCart(@Valid @RequestBody Cart cart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCart(cart).orElse(null));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Cart> addCartItem(@PathVariable Long cartId, @Valid @RequestBody CartItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCartItemToCart(cartId, item).orElse(null));
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, @Valid @RequestBody Cart cart) {
        return ResponseEntity.of(service.updateCart(cartId, cart));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        service.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
