package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductMapper;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productrepo;
	
	public Product create(ProductDTO dto) {
		if(dto.getPrice()<=0) {
			throw new BadRequestException("Price must be greater than 0");
		}
		if(dto.getStockQuantity()<0) {
			throw new BadRequestException("Stock cannot be negative");
		}
		Product product=ProductMapper.toEntity(dto);
		return productrepo.save(product);
	}
	
	public ProductDTO getProduct(Long id) {
	    Product product = productrepo.findById(id)
	        .orElseThrow(() -> new RuntimeException("Not found"));

	    return ProductMapper.toDto(product);
	}
	
	public List<Product> getAll(){
		return productrepo.findAll();
	}
	
	public Product getById(Long id) {
		return productrepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Product not found"));
	}
	
	public Product update(Long id,ProductDTO dto) {
		Product product=getById(id);
		if(dto.getPrice()<=0) {
			throw new BadRequestException("Invalid price");
		}
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setStockQuantity(dto.getStockQuantity());
		product.setDescription(dto.getDescription());
		product.setImageurl(dto.getImageUrl());
		return productrepo.save(product);
		
	}
	public void delete(Long id) {
		if (!productrepo.existsById(id)) {
	        throw new RuntimeException("Product not found");
	    }
		Product product=getById(id);
		productrepo.delete(product);
		
	}

}
