package com.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.AuthResponse;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	public AuthService authservice;
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest dto){
		return ResponseEntity.status(201).body(authservice.Register(dto));
		
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest dto){
		String token=authservice.login(dto);
		return ResponseEntity.ok(new AuthResponse(token));
	}
	

}
