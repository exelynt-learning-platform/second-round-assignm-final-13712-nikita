package com.ecommerce.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
	@NotBlank(message = "Product name is required")
	private String name;
	@NotBlank(message = "Description required")
	private String Description;
	@Positive(message = "price must be > 0")
	private double price; 
	@Min(value = 0,message = "Stock cannot be negative")
	private int stockQuantity;
	@Pattern(regexp = "^(http|https)://.*$",message = "Invalid url")
	private String imageUrl;
	

}
