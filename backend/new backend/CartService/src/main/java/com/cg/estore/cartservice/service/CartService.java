package com.cg.estore.cartservice.service;

import java.util.List;

import com.cg.estore.cartservice.entity.Cart;

// Service interface for managing shopping carts
public interface CartService {
    
    // Retrieve a cart by its cartId
    Cart getCartById(String cartId);

    // Update an existing cart
//    Cart updateCart(Cart cart);

    // Retrieve a list of all carts
    List<Cart> getAllCarts();

    // Calculate the total price of a cart
//    double cartTotal(String cartId);

    // Add a product to a cart with a specified quantity
    Cart addProductToCart(String productId,String profileId, int quantity);


	void deleteProductFromCart(String cartId);
}
