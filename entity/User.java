package com.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ecommerce.enumtype.Role;

import lombok.Data;

@Entity
@Data
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "username is required")
	@Size(min=3,max=20,message = "username must be 3-20 characters")
	private String username;
	@Email(message = "Invalid email")
	@NotBlank(message = "Email is required")
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank(message = "password is required")
	@Size(min=6, message = "password should be atleast 6 charactes")
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	

}
