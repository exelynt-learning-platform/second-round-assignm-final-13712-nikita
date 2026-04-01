package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private PaymentService paymentservice;
	@Autowired
	private OrderService orderservice;
	@PostMapping("/create/{orderId}")
	public ResponseEntity<?> pay(@AuthenticationPrincipal User user,
			@PathVariable Long orderId) {
		Order order=orderservice.getOrderById(user, orderId);
		String url=paymentservice.createPayment(order);
        return ResponseEntity.ok(url); 
    }

    
    @GetMapping("/success")
    public ResponseEntity<?> success( @RequestParam Long orderId) {

        return ResponseEntity.ok(paymentservice.markPaymentSuccess(orderId));
        
    }

    
    @GetMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam Long orderId) {
    	Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentstatus("FAILED");

        orderRepo.save(order);

        return ResponseEntity.ok("Payment cancelled");

       
	}

}
