package com.ecommerce.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JWTFilter jwtfilter;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
		http.cors().and().csrf().disable()
		.authorizeHttpRequests(auth -> auth
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/products/create", "/products/update/**", "/products/delete/**")
            .hasRole("ADMIN")
			.anyRequest().authenticated()
				)
		.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
