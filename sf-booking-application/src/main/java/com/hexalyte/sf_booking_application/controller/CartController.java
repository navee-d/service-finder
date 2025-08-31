package com.hexalyte.sf_booking_application.controller;

import com.hexalyte.sf_booking_application.model.Cart;
import com.hexalyte.sf_booking_application.model.CartItem;
import com.hexalyte.sf_booking_application.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("carts")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ofNullable(service.getCarts());
    }

    @GetMapping("users/{id}")
    private ResponseEntity<Cart> getCartByUserId(@PathVariable UUID id) {
        return ResponseEntity.of(service.getCartByUserId(id));
    }

    @PostMapping
    private ResponseEntity<Optional<Cart>> addCart(@RequestBody @Valid Cart cart) {
        return new ResponseEntity<>(service.addCart(cart), HttpStatus.CREATED);
    }

    @PostMapping("{id}")
    private ResponseEntity<Optional<Cart>> addCartItemToCart(@PathVariable Long id, @RequestBody @Valid CartItem cartItem) {
        return new ResponseEntity<>(service.addCartItemToCart(id,cartItem), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody @Valid Cart cart) {
        return ResponseEntity.of(service.updateCart(id, cart));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Cart deleted successfully")
    private void deleteCart(@PathVariable Long id){
        service.deleteCart(id);
    }

}
