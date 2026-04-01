package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	public Optional<CartItem> findByIdAndCart_User(Long cartItemId, User user);

}
