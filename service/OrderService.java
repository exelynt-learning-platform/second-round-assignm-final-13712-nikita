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
		
		Cart cart = cartRepo.findByUser(user)
	            .orElseThrow(() -> new RuntimeException("Cart not found"));

	    if (cart.getItem().isEmpty()) {
	        throw new RuntimeException("Cart is empty");
	    }

	    Order order = new Order();
	    order.setUser(user);
	    order.setShippingaddress(address);
	    order.setPaymentstatus("PENDING");

	    double total = 0;

	    List<OrderItem> orderItems = new ArrayList<>();

	    for (CartItem cartItem : cart.getItem()) {

	        OrderItem orderItem = new OrderItem();
	        orderItem.setProduct(cartItem.getProduct());
	        orderItem.setQuantity(cartItem.getQuantity());
	        orderItem.setOrder(order);

	        total += cartItem.getProduct().getPrice() * cartItem.getQuantity();

	        orderItems.add(orderItem);
	    }

	    order.setItems(orderItems);
	    order.setTotalPrice(total);
	    cart.getItem().clear();
	    cartRepo.save(cart);

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
