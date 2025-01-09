package com.spring.boot.blog.service;

import com.spring.boot.blog.payload.JWTAuthResponse;
import com.spring.boot.blog.payload.LoginDto;
import com.spring.boot.blog.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);

}
