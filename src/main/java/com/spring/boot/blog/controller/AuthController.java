package com.spring.boot.blog.controller;

import com.spring.boot.blog.payload.JWTAuthResponse;
import com.spring.boot.blog.payload.LoginDto;
import com.spring.boot.blog.payload.RegisterDto;
import com.spring.boot.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);

		JWTAuthResponse jwtResponse = new JWTAuthResponse();
		jwtResponse.setAccessToken(token);

		return new ResponseEntity<JWTAuthResponse>(jwtResponse, HttpStatus.OK);
	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
		return new ResponseEntity<String>(authService.register(registerDto), HttpStatus.CREATED);
	}

}
