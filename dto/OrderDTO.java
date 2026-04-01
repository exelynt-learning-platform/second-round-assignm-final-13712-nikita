package com.ecommerce.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class OrderDTO {
	private Long orderId;
	@Min(value = 1, message = "Quantity must be at least 1")
	private List<OrderItemDTO> items;
	@Positive(message = "Total price must be positive")
	private double totalPrice;
	private String status; 
	

}
