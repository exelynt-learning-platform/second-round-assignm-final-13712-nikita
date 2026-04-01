package com.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Product product;
	private int quantity;
	private double price;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "order_id")
	private Order order;
	

}
