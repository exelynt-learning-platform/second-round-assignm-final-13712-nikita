package com.ecommerce.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CartItemDTO {
	@NotNull
	private Long productId;
	private String productname;
	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;
	private double price;

}
