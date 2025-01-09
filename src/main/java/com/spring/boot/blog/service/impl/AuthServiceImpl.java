package com.spring.boot.blog.service.impl;

import com.spring.boot.blog.entity.Role;
import com.spring.boot.blog.entity.User;
import com.spring.boot.blog.excpetion.BlogApiException;
import com.spring.boot.blog.payload.JWTAuthResponse;
import com.spring.boot.blog.payload.LoginDto;
import com.spring.boot.blog.payload.RegisterDto;
import com.spring.boot.blog.repository.RoleRepository;
import com.spring.boot.blog.repository.UserRepository;
import com.spring.boot.blog.security.JwtTokenProvider;
import com.spring.boot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		// 1. Create authentication token
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		// 2. Set authentication in security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtTokenProvider.generateToken(authentication);

	}

	@Override
	public String register(RegisterDto registerDto) {

		// check if user already registered
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "User already registered");
		}

		// check for email is already registered
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email already registered");
		}

		// create new user
		User user = mapToEntity(registerDto);

		userRepository.save(user);

		return "User registered successfully";
	}

	private User mapToEntity(RegisterDto registerDto) {
		// registerDto.setPassword(registerDto.getPassword());

		// create new user
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());

		// encode password
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		// assign role to user
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER")
			.orElseThrow(() -> new BlogApiException(HttpStatus.NOT_FOUND, "User role not found"));
		roles.add(userRole);
		user.setRoles(roles);
		return user;
	}

}
