package com.ecommerce.dto;

import java.util.List;
import lombok.Data;
@Data
public class CartDTO {
	
	private Long userId;
	private List<CartItemDTO> items;
	private double totalPrice;

}
