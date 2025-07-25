package com.cg.estore.paymentservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.cg.estore.paymentservice.entity.Cart;

import java.util.List;

@FeignClient(name = "cart-service", url = "http://localhost:9012/carts")
public interface CartFeignClient {

    @GetMapping("/getCartById/{cartId}")
    public Cart getCartById(@PathVariable("cartId") String cartId);

    @PostMapping("/{cartId}/clear")
    void clearCart(@PathVariable("cartId") String cartId);

    @DeleteMapping("/{cartId}/removeItem/{itemId}")
    void removeItemFromCart(@PathVariable("cartId") String cartId, @PathVariable("itemId") String itemId);

    @PostMapping("/{cartId}/checkout")
    void checkoutCart(@PathVariable("cartId") String cartId);

}

