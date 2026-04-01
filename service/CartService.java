package com.ecommerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;

@Service
public class CartService {
	@Autowired
	public CartRepository cartrepo;
	@Autowired
	public ProductRepository productrepo;
	@Autowired
	public CartItemRepository cartitemRepo;
	
	public Cart addtoCart(User user, Long productId, int quantity) {
		if(quantity <=0) {
			throw new BadRequestException("Quantity must be grater than 0");
		}
		Product product=productrepo.findById(productId)
				.orElseThrow(()-> new ResourceNotFoundException("Product not found"));
	
		Cart cart=cartrepo.findByUser(user)
				.orElseGet(() -> {
					Cart newcart=new Cart();
					newcart.setUser(user);
					newcart.setItem(new ArrayList<>());
					return cartrepo.save(newcart);
					
				});
		int totalQuantity = quantity;
		Optional<CartItem> existingItem = cart.getItem().stream()
		        .filter(i -> i.getProduct().getId().equals(productId))
		        .findFirst();

		if (existingItem.isPresent()) {
		    totalQuantity += existingItem.get().getQuantity();
		}

		if (totalQuantity > product.getStockQuantity()) {
		    throw new BadRequestException("Not enough stock available");
		}
	
		CartItem item=new CartItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(quantity);
		cart.getItem().add(item);
		cartrepo.save(cart);
		return cart;
	}
	
	public Cart getCart(User user) {
		return cartrepo.findByUser(user)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
	}
	
	public Cart updateCart(User user, Long cartItemId, int quantity) {
		
		if(quantity <=0) {
			throw new BadRequestException("Quantity must be greater than 0");
		}
		Cart cart = cartrepo.findByUser(user)
		        .orElseThrow(() -> new RuntimeException("Cart not found"));
		
		CartItem item=cartitemRepo.findByIdAndCart_User(cartItemId, user)
				.orElseThrow(()-> new ResourceNotFoundException("Item not found"));
		if (!item.getCart().getUser().getId().equals(user.getId())) {
		    throw new BadRequestException("Access denied");
		}
		item.setQuantity(quantity);
		return cartrepo.save(item.getCart());
	}
	
	public Cart clearCart(User user,Long cartItemId) {
		
		
		CartItem item=cartitemRepo.findByIdAndCart_User(cartItemId, user)
				.orElseThrow(()-> new ResourceNotFoundException("Item not found"));
		Cart cart=item.getCart();
		cart.getItem().remove(item);
		return cartrepo.save(cart);
		
		
	}
	

}
