package com.cg.estore.cartservice.repository;

import com.cg.estore.cartservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProductName(String productName);

    List<Product> findByCategory(String category);

    List<Product> findByProductType(String productType);

    void deleteByProductId(String cartId);

    Optional<Product> findByProductId(String productId);
}
