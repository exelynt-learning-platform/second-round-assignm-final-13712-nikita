package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@RequestParam String address, @AuthenticationPrincipal User user){
		Order order=orderservice.createOrder(user, address);
		return ResponseEntity.status(HttpStatus.CREATED).body( order);
	}
	
	@GetMapping("/myorders")
	public ResponseEntity<?> getOrders(@AuthenticationPrincipal User user){
		return ResponseEntity.ok(orderservice.getuserOrders(user));
	}
	
	@GetMapping("/getorderById/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable Long orderId, @AuthenticationPrincipal User user){
		return ResponseEntity.ok(orderservice.getorderById(user, orderId));
	}
	@PutMapping("/update/{orderId}")
	public ResponseEntity<?> updatePayment(@PathVariable Long orderId,@RequestParam String status){
		orderservice.updatePaymentStatus(orderId, status);
		return ResponseEntity.ok("Payment status updated");
	}

}
