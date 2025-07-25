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
public class TransactionDetails {
	
	 private String orderId;
	    private String currency;
	    private Integer amount;
	    private String key;

}
