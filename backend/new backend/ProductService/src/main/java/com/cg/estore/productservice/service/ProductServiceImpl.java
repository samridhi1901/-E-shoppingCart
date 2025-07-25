package com.cg.estore.productservice.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.cg.estore.productservice.entity.Product;
import com.cg.estore.productservice.exception.ProductNotFoundException;
import com.cg.estore.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    ProductRepository productRepository;
    
	@Override
	public Product addProduct(Product product) {
		
		Product products=new Product();
		products.setProductId(product.getProductId());
		products.setProductType(product.getProductType());
		products.setProductName(product.getProductName());
		products.setCategory(product.getCategory());
		products.setDescription(product.getDescription());
	products.setImage(product.getImage());
		products.setTotalPrice(product.getTotalPrice());
		
		products.setQuantity(product.getQuantity());
		
		
		return productRepository.save(products);
		
	}
    
    
    

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(String productId) {
		
		return productRepository.findByProductId(productId);
	}
	@Override
    public List<Product> getProductsByName(String productName){
	    return productRepository.findByProductName(productName);
	}

@Override
	public Product updateProduct(Product product) {
		Product product2 = productRepository.findById(product.getProductId()).get();
		product2.setProductId(product.getProductId());
		product2.setProductName(product.getProductName());
		product2.setProductType(product.getProductType());
		product2.setImage(product.getImage());
		product2.setCategory(product.getCategory());
		product2.setDescription(product.getDescription());
		product2.setQuantity(product.getQuantity());
		product2.setTotalPrice(product.getTotalPrice());
		return productRepository.save(product);
	}


	@Transactional
	public void deleteProductById(String productId) {
		if (!productRepository.existsById(productId)) {
			throw new EntityNotFoundException("Product not found with ID: " + productId);
		}
		productRepository.deleteByProductId(productId);
	}


	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> getProductsByType(String productType) {
		
		return productRepository.findByProductType(productType);
	}
	
	
	   @Override
	    public void updateProductQuantity(String productId,int quantity) {
	//	Product product = productRepository.findByProductId(productId).get();
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

		product.setQuantity(product.getQuantity()-(quantity));
		
		 productRepository.save(product);
	}
	
	   @Override
	   public String[] getCategories() {
	        List<Product> products = productRepository.findAll();
	        List<String> categories = products.stream()
	                                          .map(Product::getCategory)
	                                          .distinct()
	                                          .collect(Collectors.toList());
	        return categories.toArray(new String[0]);
	    }
    
}
