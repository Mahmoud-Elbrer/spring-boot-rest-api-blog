package com.spring.boot.blog.payload;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDetails {

	private Date timestamp;

	private String message;

	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

}
