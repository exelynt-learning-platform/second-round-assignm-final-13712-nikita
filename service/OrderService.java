package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private CartRepository cartRepo;;
	
	public Order createOrder(User user,String address) {
		
		Cart cart=cartRepo.findByUser(user)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
		if(cart.getItem().isEmpty()) {
			throw new BadRequestException("cart is empty");
		}
		
		Order order=new Order();
		order.setUser(user);
		order.setShippingaddress(address);
		order.setPaymentstatus("Pending");
		double total=0;
		for(CartItem item:cart.getItem()) {
			total+= item.getProduct().getPrice() * item.getQuantity();
		}
		order.setTotalPrice(total);
		List<OrderItem> orderitems=new ArrayList<>();
		for(CartItem cartitem:cart.getItem()) {
			OrderItem orderitem=new OrderItem();
			double price = cartitem.getProduct().getPrice();
			if(price <= 0) {
			    throw new RuntimeException("Invalid product price");
			}
			orderitem.setPrice(cartitem.getProduct().getPrice());
			orderitem.setProduct(cartitem.getProduct());
			orderitem.setQuantity(cartitem.getQuantity());
			orderitem.setOrder(order);
			orderitems.add(orderitem);
		}
		order.setItems(orderitems);
		cart.getItem().clear();		
		return orderRepo.save(order);
		
	
	}
	public List<Order> getuserOrders(User user){
		return orderRepo.findByUser(user);
	}
	public Order getOrderById(User user, Long orderId ) {
		Order order=orderRepo.findById(orderId)
				.orElseThrow(()-> new ResourceNotFoundException("Order not found"));
		if(!order.getUser().getId().equals(user.getId())) {
			throw new BadRequestException("Unauthorized" );
		}
		return order;
	}
	public Order updatePaymentStatus(Long orderId,String status) {
		Order order=orderRepo.findById(orderId)
				.orElseThrow(()-> new ResourceNotFoundException("Order not Found!!"));
		order.setPaymentstatus(status);
		return orderRepo.save(order);
	}

}
