package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductMapper;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productservice;
	@PostMapping("/create")
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto){
		return ResponseEntity.status(201).body(ProductMapper.toDto(productservice.create(dto)));
	}
	@GetMapping("allproducts")
	public ResponseEntity<List<ProductDTO>> getAll(){
		List<Product> products=productservice.getAll();
		List<ProductDTO> dtolist=new ArrayList<>();
		for(Product p: products) {
			dtolist.add(ProductMapper.toDto(p));
		}
		return ResponseEntity.ok(dtolist);
		
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(ProductMapper.toDto(productservice.getById(id)));
		
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody ProductDTO dto){
		return ResponseEntity.ok(ProductMapper.toDto(productservice.update(id, dto)));
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		productservice.delete(id);
		return ResponseEntity.ok("product deleted");
	}
	

}
