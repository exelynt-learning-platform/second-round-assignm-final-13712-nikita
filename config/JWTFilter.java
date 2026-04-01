package com.ecommerce.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
@Component
public class JWTFilter extends OncePerRequestFilter{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JWTUtil jwtutil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String header = request.getHeader("Authorization");

	    if (header != null && header.startsWith("Bearer ")) {

	        String token = header.substring(7);
	        String username = jwtutil.extractUsername(token);

	        User user = userRepo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        List<SimpleGrantedAuthority> authorities =
	                Collections.singletonList(
	                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
	                );

	        UsernamePasswordAuthenticationToken auth =
	                new UsernamePasswordAuthenticationToken(user, null, authorities);

	        SecurityContextHolder.getContext().setAuthentication(auth);
	    }

	    filterChain.doFilter(request, response);
	}
	

}
