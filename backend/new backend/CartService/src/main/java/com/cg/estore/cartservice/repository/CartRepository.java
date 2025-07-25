package com.cg.estore.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cg.estore.cartservice.entity.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    // Example custom query methods (uncomment and implement as needed)
    // Cart findByCartId(String cartId);
    // List<Cart> findByProductId(String productId);
    // List<Cart> findProductCountByCartId(String cartId);
}
