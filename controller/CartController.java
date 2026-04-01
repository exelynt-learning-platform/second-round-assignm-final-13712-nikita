package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;
import com.ecommerce.service.CartService;
@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartservice;
	@PostMapping("/add/{productId}")
	public ResponseEntity<?> addToCart(@PathVariable Long productId,
			@RequestParam int quantity,
			@AuthenticationPrincipal User user){
		return ResponseEntity.ok(cartservice.addtoCart(user, productId, quantity));
		
	}
	@GetMapping("/getcart")
	public ResponseEntity<?> getCart(@AuthenticationPrincipal User user){
		return ResponseEntity.ok(cartservice.getCart(user));
	}
	@PutMapping("/updatecart")
	public ResponseEntity<?> updateCart(@AuthenticationPrincipal User user, @PathVariable Long cartItemId, @RequestParam int quantity){
		return ResponseEntity.ok(cartservice.updateCart(user, cartItemId, quantity));
	}
	@DeleteMapping("/clear/{cartItemId}")
	public ResponseEntity<?> clearCart(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
		cartservice.clearCart(user, cartItemId);
		return ResponseEntity.ok("Cart is cleared");
	}

}
