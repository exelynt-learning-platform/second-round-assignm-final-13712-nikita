package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.config.JWTUtil;
import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.RegisterRequest;
import com.ecommerce.entity.User;
import com.ecommerce.enumtype.Role;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JWTUtil jwtutil;
	
	public String Register(RegisterRequest dto) {
		
		if(userRepo.findByUsername(dto.getUsername()).isPresent()) {
			throw new BadRequestException("Username already exists");
		}
		if(userRepo.findByEmail(dto.getEmail()).isPresent()) {
			throw new BadRequestException("Email already exists");
		}
		User user=new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(encoder.encode(dto.getPassword()));
		if("ADMIN_SECRET".equals(dto.getAdminkey())) {
		    user.setRole(Role.ADMIN);
		} else {
		    user.setRole(Role.USER);
		}
		userRepo.save(user);
		return "User registered successfully!!";
		
	}
	public String login(AuthRequest dto) {
		User user=userRepo.findByUsername(dto.getUsername())
		.orElseThrow(()->new ResourceNotFoundException("User not found!!"));
		if(!encoder.matches(dto.getPassword(), user.getPassword())) {
			throw new UnauthorizedException("Invalid Password");
		}
		return jwtutil.generateToken(user.getUsername());
	}
	
	
	
	

}
