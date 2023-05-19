package com.gorl.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gorl.demo.dto.LoginDto;
import com.gorl.demo.dto.RegisterDto;
import com.gorl.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<String> login(@RequestBody LoginDto dto)
	{
		String value = authService.login(dto);
		
		return ResponseEntity.ok(value);
	}
	
	@PostMapping(value = {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto dto)
	{
		String value = authService.register(dto);
		
		return new ResponseEntity<>(value, HttpStatus.CREATED);
	}
}
