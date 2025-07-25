package com.cg.estore.cartservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    private String cartId;

    private double totalPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id") // foreign key in Product table
    private List<Product> productsAdded;

	public void setQuantity(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setProductName(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setProductId(String string) {
		// TODO Auto-generated method stub
		
	}

	public Integer getQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getProductId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getProductName() {
		// TODO Auto-generated method stub
		return null;
	}
}
