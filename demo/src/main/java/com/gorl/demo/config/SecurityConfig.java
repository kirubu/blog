package com.gorl.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private UserDetailsService userDetailsService;
	
	
	public SecurityConfig(UserDetailsService userDetailsService) {
		
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
		
	}

	@Bean
	static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.csrf().disable()
			.authorizeHttpRequests((authorize) -> 
			//authorize.anyRequest().authenticated()
			authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
			.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
			.anyRequest().authenticated()
					).httpBasic();
		
		return httpSecurity.build();
	}
	
	/*
	@Bean
	public UserDetailsService userDetailsService()
	{
		UserDetails kiruba = User.builder()
				.username("kiruba")
				.password(passwordEncoder().encode("1234"))
				.roles("user")
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("12345"))
				.roles("admin")
				.build();
		
		return new InMemoryUserDetailsManager(kiruba, admin);
	}*/
}
