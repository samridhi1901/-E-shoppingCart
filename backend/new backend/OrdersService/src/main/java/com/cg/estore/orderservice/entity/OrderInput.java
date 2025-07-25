package com.cg.estore.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInput {

	 private String fullName;
	    private String fullAddress;
	    private String contactNumber;
	    private String alternateContactNumber;
	    private String productName;
	    private int quantity;
	    
	    private String transactionId;
}
