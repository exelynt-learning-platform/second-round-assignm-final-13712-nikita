package com.ecommerce.dto;

import java.util.List;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class OrderDTO {
	private Long orderId;
	@NotEmpty
	private List<OrderItemDTO> items;
	@Positive(message = "Total price must be positive")
	private double totalPrice;
	private String status; 
	

}
