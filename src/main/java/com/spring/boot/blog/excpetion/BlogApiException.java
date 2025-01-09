package com.spring.boot.blog.excpetion;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
// we throw this exception whenever we write some business logic or validation request
// parameters
public class BlogApiException extends RuntimeException {

	private final HttpStatus status;

	private final String message;

	public BlogApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public BlogApiException(String message, HttpStatus status, String message1) {
		super(message);
		this.status = status;
		this.message = message1;
	}

}
