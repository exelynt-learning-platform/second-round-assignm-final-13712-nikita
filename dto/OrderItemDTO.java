package com.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
	
	private String productName;
	private int quantity;
	private double price;

}
