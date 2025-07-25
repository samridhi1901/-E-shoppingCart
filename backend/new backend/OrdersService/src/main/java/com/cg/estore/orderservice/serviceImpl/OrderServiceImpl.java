package com.cg.estore.orderservice.serviceImpl;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.estore.orderservice.entity.Cart;
import com.cg.estore.orderservice.entity.OrderInput;
import com.cg.estore.orderservice.entity.OrderDetail;
import com.cg.estore.orderservice.entity.Product;
import com.cg.estore.orderservice.entity.TransactionDetails;
import com.cg.estore.orderservice.entity.User;

import com.cg.estore.orderservice.exception.OrderNotFoundException;
import com.cg.estore.orderservice.repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private PaymentFeignClient paymentFeignClient;
    
    @Autowired
    private ProductFeignClient productFeignClient;
    
    @Autowired
    private CartFeignClient cartFeignClient;
    
    @Autowired
    private UserFeignClient userFeignClient;
    
    private static final String ORDER_PLACED = "Placed";

    private static final String KEY = "rzp_test_SLQX0ojm9Jn3nc";
    private static final String KEY_SECRET = "rHsxdes8rd8EkQ2V12SHZxQi";
    private static final String CURRENCY = "INR";


//    @Override
//    public Orders placeOrder(String paymentId) {
//        try {
//            // Fetch payment details from Payment service using Feign Client
//            Payment payment = paymentFeignClient.getPaymentById(paymentId);
//     
//            if (payment == null) {
//                throw new OrderNotFoundException("Payment not found with ID: " + paymentId);
//            }
//            // Further processing steps...
//            Orders order = new Orders();
//            // Set other order details from the payment
//            order.setTotalPrice(payment.getTotalPrice());
//            order.setOrderStatus("Order Placed");
//            order.setOrderDate(payment.getPaymentDate());
//           order.setCart(cartFeignClient.getCartById(payment.getCartId()));
//          order.setProfileId(payment.getUserprofile().getProfileId());
//            // Set other order details as needed from the payment
//         Cart cart = cartFeignClient.getCartById(payment.getCartId());
//         cart.setQuantity(cart.getQuantity()- (order.getCart().getQuantity()));
//            // Save the order
//            Orders savedOrder = orderRepository.save(order);
//            return savedOrder;
//        } catch (OrderNotFoundException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw new RuntimeException("An error occurred while processing the request.", ex);
//        }
//    }
    @Override
    public OrderDetail placeOrder(OrderDetail orderDetail) {
        try {
        	
            User user = userFeignClient.getProfileByFullName(orderDetail.getFullName());
          
            List<Product> products = productFeignClient.getProductsByName(orderDetail.getProductName());


if (products.isEmpty()) {
    throw new RuntimeException("No product found with name: " + orderDetail.getProductName());
}

Product product = products.get(0); // or apply some filtering logic
            double orderPrice = product.getTotalPrice() * orderDetail.getQuantity();

            OrderDetail orders = new OrderDetail(orderPrice, "ORDER PLACED",
                    orderDetail.getProductName(), orderDetail.getQuantity(), orderDetail.getContactNumber(),
                    orderDetail.getFulladdress(), user.getProfileId(), orderDetail.getTransactionId(),orderDetail.getCartId(), orderDetail.getFullName());

            return orderRepository.save(orders);
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace to console
            throw new RuntimeException("Error placing order: " + e.getMessage());
        }
    }

    @Override
    public OrderDetail getOrderById(String orderId) {
        Optional<OrderDetail> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }
    
    @Override
    public List<OrderDetail> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @Override
    public void deleteOrderById(String orderId) {
        // Check if the order exists
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        
        // Delete the order by ID
        orderRepository.deleteById(orderId);
    }
    
    public OrderDetail getOrderByProfileId(String profileId) {
    	
    	OrderDetail order = orderRepository.findOrderByProfileId(profileId);
    	if (order==null) {
            throw new OrderNotFoundException("No Orders with : " + profileId);
        }
		return order;
    	
    }
    
    @Override
    public TransactionDetails createTransaction(Double amount) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100) );
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
            return transactionDetails;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
        return transactionDetails;
    }

}
