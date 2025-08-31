package com.hexalyte.sf_booking_application.service.impl;

import com.hexalyte.sf_booking_application.model.Cart;
import com.hexalyte.sf_booking_application.model.CartItem;
import com.hexalyte.sf_booking_application.model.DiscountType;
import com.hexalyte.sf_booking_application.model.feign.SolutionDTO;
import com.hexalyte.sf_booking_application.repository.CartRepository;
import com.hexalyte.sf_booking_application.service.CartService;
import com.hexalyte.sf_booking_application.service.feign.ServiceInterface;
import com.hexalyte.sf_booking_application.service.feign.UserInterface;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final UserInterface userInterface;
    private final ServiceInterface serviceInterface;

    public CartServiceImpl(CartRepository repository, UserInterface userInterface, ServiceInterface serviceInterface) {
        this.repository = repository;
        this.userInterface = userInterface;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public List<Cart> getCarts() {
        List<Cart> carts = repository.findAll();
        if (carts.isEmpty())
            return null;
        return carts;
    }

    @Override
    public Optional<Cart> getCartByUserId(UUID id) {
        try {
            userInterface.getUserById(id);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with such ID");
        }
        return repository.findByUserId(id);
    }

    @Override
    public Optional<Cart> addCart(Cart cart) {

        List<CartItem> cartItems = cart.getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            for (int j = i + 1; j < cartItems.size(); j++) {
                if (cartItems.get(i).getServiceId() == cartItems.get(j).getServiceId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate cart items");
            }
        }

        try {
            userInterface.getUserById(cart.getUserId());
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with such ID");
        }

        BigDecimal calculatedTotal = BigDecimal.valueOf(0);
        for (CartItem cartItem : cartItems) {

            try {
                SolutionDTO service = serviceInterface.getServiceById(cartItem.getServiceId()).getBody();

                if (!cartItem.getServiceProviderId().equals(service.getServiceProviderId()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service provider does not provide the " + service.getName() + " service");

                if (!service.getIsActive())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, service.getName() + " is not active");

                if (!service.getIsAvailable())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, service.getName() + " is not available");

                BigDecimal servicePrice = BigDecimal.valueOf(service.getPrice()).setScale(2, RoundingMode.UNNECESSARY);
                cartItem.setGrossPrice(
                        servicePrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                );

                BigDecimal calculatedNetPrice = cartItem.getGrossPrice();
                if (cartItem.getDiscount() != null) {
                    if (cartItem.getDiscount().getDiscountType().equals(DiscountType.Percentage))
                        calculatedNetPrice = calculatedNetPrice.subtract(
                                calculatedNetPrice.multiply(
                                        cartItem.getDiscount().getValue()
                                ).divide(
                                        BigDecimal.valueOf(100),
                                        2,
                                        RoundingMode.UNNECESSARY
                                )
                        );
                    else if (cartItem.getDiscount().getDiscountType().equals(DiscountType.Price_deduction))
                        calculatedNetPrice = calculatedNetPrice.subtract(cartItem.getDiscount().getValue());
                }

                cartItem.setNetPrice(calculatedNetPrice);

            } catch (FeignException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No service found with ID: " + cartItem.getServiceId());
            }

            calculatedTotal = calculatedTotal.add(cartItem.getNetPrice());
        }

        cart.setTotal(calculatedTotal);

        return Optional.of(repository.save(cart));
    }

    @Override
    public Optional<Cart> addCartItemToCart(Long id, CartItem cartItem) {
        Cart cart = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find cart with such ID")
        );

        List<CartItem> cartItems = cart.getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            for (int j = i + 1; j < cartItems.size(); j++) {
                if (cartItems.get(i).getServiceId() == cartItems.get(j).getServiceId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate cart items");
            }
        }

        cartItem.setCart(cart);
        cartItems.add(cartItem);

        BigDecimal calculatedTotal = BigDecimal.valueOf(0);
        for (CartItem updatingCartItem : cartItems) {

            try {
                SolutionDTO service = serviceInterface.getServiceById(updatingCartItem.getServiceId()).getBody();

                if (!updatingCartItem.getServiceProviderId().equals(service.getServiceProviderId()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service provider does not provide the " + service.getName() + " service");

                if (!service.getIsActive())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, service.getName() + " is not active");

                if (!service.getIsAvailable())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, service.getName() + " is not available");

                BigDecimal servicePrice = BigDecimal.valueOf(service.getPrice()).setScale(2, RoundingMode.UNNECESSARY);
                updatingCartItem.setGrossPrice(
                        servicePrice.multiply(BigDecimal.valueOf(updatingCartItem.getQuantity()))
                );

                BigDecimal calculatedNetPrice = updatingCartItem.getGrossPrice();
                if (updatingCartItem.getDiscount() != null) {
                    if (updatingCartItem.getDiscount().getDiscountType().equals(DiscountType.Percentage))
                        calculatedNetPrice = calculatedNetPrice.subtract(
                                calculatedNetPrice.multiply(
                                        updatingCartItem.getDiscount().getValue()
                                ).divide(
                                        BigDecimal.valueOf(100),
                                        2,
                                        RoundingMode.UNNECESSARY
                                )
                        );
                    else if (updatingCartItem.getDiscount().getDiscountType().equals(DiscountType.Price_deduction))
                        calculatedNetPrice = calculatedNetPrice.subtract(updatingCartItem.getDiscount().getValue());
                }

                updatingCartItem.setNetPrice(calculatedNetPrice);

            } catch (FeignException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No service found with ID: " + updatingCartItem.getServiceId());
            }

            calculatedTotal = calculatedTotal.add(updatingCartItem.getNetPrice());
        }

        cart.setTotal(calculatedTotal);

        return Optional.of(repository.save(cart));
    }

    @Override
    public Optional<Cart> updateCart(Long id, Cart cart) {
        Cart updatingCart = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find cart with such ID")
        );

        try {
            userInterface.getUserById(updatingCart.getUserId());
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with such ID");
        }

        List<CartItem> retainingItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
                for (int i = 0; i < updatingCart.getCartItems().size(); i++) {
                    CartItem updatingCartItem = updatingCart.getCartItems().get(i);
                    if (updatingCartItem.getCartItemId().equals(cartItem.getCartItemId())) {
                        updatingCartItem.setQuantity(cartItem.getQuantity());
                        updatingCartItem.setDiscount(cartItem.getDiscount());
                        updatingCartItem.setDescription(cartItem.getDescription());
                        retainingItems.add(updatingCartItem);
                        break;
                    }
                }
        }

        updatingCart.getCartItems().retainAll(retainingItems);

        List<CartItem> updatingCartItems = updatingCart.getCartItems();

        for (int i = 0; i < updatingCartItems.size(); i++) {
            for (int j = i + 1; j < updatingCartItems.size(); j++) {
                if (updatingCartItems.get(i).getServiceId() == updatingCartItems.get(j).getServiceId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate cart items");
            }
        }

        BigDecimal calculatedTotal = BigDecimal.valueOf(0);
        for (CartItem updatingAndAddingCartItem : updatingCartItems) {
            System.out.println(updatingAndAddingCartItem.getServiceId());
            try {
                SolutionDTO service = serviceInterface.getServiceById(updatingAndAddingCartItem.getServiceId()).getBody();

                if (!updatingAndAddingCartItem.getServiceProviderId().equals(service.getServiceProviderId()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service provider does not provide the " + service.getName() + " service");

                if (!service.getIsActive())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, service.getName() + " is not active");

                if (!service.getIsAvailable())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, service.getName() + " is not available");

                BigDecimal servicePrice = BigDecimal.valueOf(service.getPrice()).setScale(2, RoundingMode.UNNECESSARY);
                updatingAndAddingCartItem.setGrossPrice(
                        servicePrice.multiply(BigDecimal.valueOf(updatingAndAddingCartItem.getQuantity()))
                );

                BigDecimal calculatedNetPrice = updatingAndAddingCartItem.getGrossPrice();
                if (updatingAndAddingCartItem.getDiscount() != null) {
                    if (updatingAndAddingCartItem.getDiscount().getDiscountType().equals(DiscountType.Percentage))
                        calculatedNetPrice = calculatedNetPrice.subtract(
                                calculatedNetPrice.multiply(
                                        updatingAndAddingCartItem.getDiscount().getValue()
                                ).divide(
                                        BigDecimal.valueOf(100),
                                        2,
                                        RoundingMode.HALF_EVEN
                                )
                        );
                    else if (updatingAndAddingCartItem.getDiscount().getDiscountType().equals(DiscountType.Price_deduction))
                        calculatedNetPrice = calculatedNetPrice.subtract(updatingAndAddingCartItem.getDiscount().getValue());
                }

                updatingAndAddingCartItem.setNetPrice(calculatedNetPrice);

            } catch (FeignException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No service found with ID: " + updatingAndAddingCartItem.getServiceId());
            }

            calculatedTotal = calculatedTotal.add(updatingAndAddingCartItem.getNetPrice());
        }

        updatingCart.setTotal(calculatedTotal);
        System.out.println(calculatedTotal);

        return Optional.of(repository.save(updatingCart));
    }

    @Override
    public void deleteCart(Long id) {
        repository.delete(
                repository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find cart with such ID")
                )
        );
    }
}
