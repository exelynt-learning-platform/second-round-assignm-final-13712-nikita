package com.ecommerce.dto;

import com.ecommerce.entity.Product;

public class ProductMapper {
	
	public static Product toEntity(ProductDTO dto) {
		Product p=new Product();
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setStockQuantity(dto.getStockQuantity());
		p.setPrice(dto.getPrice());
		p.setImageurl(dto.getImageUrl());
		return p;
	}
	public static ProductDTO toDto(Product p) {
		ProductDTO dto=new ProductDTO();
		dto.setId(p.getId());
		dto.setName(p.getName());
		dto.setDescription(p.getDescription());
		dto.setStockQuantity(p.getStockQuantity());
		dto.setPrice(p.getPrice());
		dto.setImageUrl(p.getImageurl());
		return dto;
	}

}
