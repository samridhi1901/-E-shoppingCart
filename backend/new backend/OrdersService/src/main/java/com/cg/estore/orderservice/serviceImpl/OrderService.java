package com.cg.estore.orderservice.serviceImpl;

import java.math.BigInteger;
import java.util.List;

import com.cg.estore.orderservice.entity.OrderInput;
import com.cg.estore.orderservice.entity.OrderDetail;
import com.cg.estore.orderservice.entity.TransactionDetails;

public interface OrderService {

    
   // Orders placeOrder(String paymentId);
	OrderDetail placeOrder(OrderDetail orderDetail);


    OrderDetail getOrderById(String orderId);

	List<OrderDetail> getAllOrders();

	void deleteOrderById(String orderId);
	
    OrderDetail getOrderByProfileId(String profileId);
    TransactionDetails createTransaction(Double amount);

}
