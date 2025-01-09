package com.spring.boot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private Long id;

	@NotEmpty(message = "Name should not be empty or null")
	private String name;

	@NotEmpty(message = "Email should not be empty or null")
	@Email
	private String email;

	@NotEmpty(message = "email must not be empty")
	@Size(min = 2, message = "Content must be at least 2 characters ")
	private String content;

}
