package com.spring.boot.blog.controller;

import com.spring.boot.blog.payload.JWTAuthResponse;
import com.spring.boot.blog.payload.LoginDto;
import com.spring.boot.blog.payload.RegisterDto;
import com.spring.boot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authentication",
        description = "Operations related to authentication"
)
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Authenticate user",
            description = "Authenticate user with provided credentials"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User authenticated successfully"
    )
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {

        String token = authService.login(loginDto);

        JWTAuthResponse jwtResponse = new JWTAuthResponse();
        jwtResponse.setAccessToken(token);

        return new ResponseEntity<JWTAuthResponse>(jwtResponse, HttpStatus.OK);
    }


    @Operation(
            summary = "Register new user",
            description = "Register new user with provided details"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully"
    )
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        return new ResponseEntity<String>(authService.register(registerDto), HttpStatus.CREATED);
    }

}
