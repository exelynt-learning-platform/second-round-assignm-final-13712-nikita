package com.ecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class RegisterRequest {
	
	@NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String username;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6)
    private String password;
    private String adminkey;

}
