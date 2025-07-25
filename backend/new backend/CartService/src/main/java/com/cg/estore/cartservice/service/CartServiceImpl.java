package com.cg.estore.cartservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.estore.cartservice.entity.Cart;
import com.cg.estore.cartservice.entity.Product;
import com.cg.estore.cartservice.entity.User;
import com.cg.estore.cartservice.exception.CartNotFoundException;
import com.cg.estore.cartservice.exception.CartTotalCalculationException;
import com.cg.estore.cartservice.exception.ProductNotFoundException;
import com.cg.estore.cartservice.repository.CartRepository;
import com.cg.estore.cartservice.repository.ProductRepository;
import com.cg.estore.cartservice.service.CartService;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.MediaType;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Autowired
	private UserFeignClient userFeignClient;

	// Add an appropriate logger (e.g.,
	// LoggerFactory.getLogger(CartServiceImpl.class))

//    @Override
//    public Cart addProductToCart(String productId, Cart cart, int quantity) {
//        // Fetch product details using Feign Client
//        Product product = productFeignClient.getProductById(productId);
//
//        if (product != null && product.getQuantity()>= quantity) {
//            Cart cart1 = new Cart();  
//            // Copying product details to the new cart
//            cart1.setProductId(product.getProductId());
//            cart1.setQuantity(quantity);  // Use the provided quantity
//            cart1.setProductName(product.getProductName());
//            cart1.setTotalPrice(product.getTotalPrice() * quantity);
//      
//            productFeignClient.updateProductQuantity( productId,quantity);
//            
//            return cartRepository.save(cart1);
//        } else {
//            // Either return an empty cart or throw an exception
//            // Returning an empty cart, modify this according to your application logic
//            return new Cart();
//        }
//    }


	@Override
	public Cart addProductToCart(String productId, String profileId, int quantity) {
		// Fetch product details from Product Service
		Product product = productFeignClient.getProductById(productId);

		if (product == null || product.getQuantity() < quantity) {
			throw new ProductNotFoundException("Product not found or insufficient stock.");
		}

		// Fetch or create cart
		Cart cart = cartRepository.findById(profileId).orElse(new Cart());
		if (cart.getCartId() == null) {
			cart.setCartId(profileId);
			cart.setProductsAdded(new ArrayList<>());
			cart.setTotalPrice(0.0);
		}

		List<Product> products = cart.getProductsAdded();
		boolean found = false;

		for (Product item : products) {
			if (item.getProductId().equalsIgnoreCase(productId)) {
				// Product already in cart, update quantity
				item.setQuantity(item.getQuantity() + quantity);
				found = true;
				break;
			}
		}

		if (!found) {
			// Clone product to avoid modifying original inventory
			Product cartProduct = new Product();
			cartProduct.setProductId(product.getProductId());
			cartProduct.setProductName(product.getProductName());
			cartProduct.setProductType(product.getProductType());
			cartProduct.setCategory(product.getCategory());
			cartProduct.setImage(product.getImage());
			cartProduct.setDescription(product.getDescription());
			cartProduct.setTotalPrice(product.getTotalPrice());
			cartProduct.setQuantity(quantity); // Set quantity in cart

			products.add(cartProduct);
		}

		// Update inventory quantity
		productFeignClient.updateProductQuantity(productId, -quantity); // subtract from stock

		// Recalculate total price
		double total = 0.0;
		for (Product p : products) {
			total += p.getTotalPrice() * p.getQuantity();
		}
		cart.setTotalPrice(total);

		return cartRepository.save(cart);
	}



	@Override
	public List<Cart> getAllCarts() {
		List<Cart> carts = cartRepository.findAll();
		if (carts.isEmpty()) {
			throw new CartNotFoundException("No carts found.");
		}
		return carts;
	}

	@Override
	public Cart getCartById(String cartId) {
		return cartRepository.findById(cartId).orElse(new Cart());
	}

//    @Override
//    public Cart updateCart(Cart cart) {
//        Cart existingCart = cartRepository.findByCartId(cart.getCartId());
//
//        if (existingCart != null) {
//            // Update the cart
//            existingCart.setProductId(cart.getProductId());
//            existingCart.setProductName(cart.getProductName());
//            existingCart.setQuantity(cart.getQuantity());
//            existingCart.setTotalPrice(cart.getTotalPrice()*cart.getQuantity());
//
//            return cartRepository.save(existingCart);
//        } else {
//            throw new CartNotFoundException("Cart not found with ID: " + cart.getCartId());
//        }
//    }

	@Override
//    public double cartTotal(String cartId) {
//        double totalPrice = 0.0;
//
//    	Cart cart = cartRepository.findByCartId(cartId);
//    	if (cart != null ) {
//    	totalPrice = cart.getTotalPrice();
//        } else {
//            throw new CartTotalCalculationException("Invalid cart details for calculating total price.");
//        }
//
//        return totalPrice;
//    }

	public void deleteProductFromCart(String cartId) {
		Cart cart = cartRepository.findById(cartId).get();
		if (cart != null) {
			cartRepository.delete(cart);
		} else {
			throw new CartNotFoundException("Cart not found with ID: " + cartId);
		}
	}
}
