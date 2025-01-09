package com.spring.boot.blog.excpetion;

import com.spring.boot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handle specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// Global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// handle exception Arguments exception
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// handle Authorization Denied Exception
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(AuthorizationDeniedException exception,
			WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	// you can use this rather than above but remove : extends
	// ResponseEntityExceptionHandler
	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// public ResponseEntity<Object>
	// handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
	// WebRequest request) {
	//
	// Map<String, String> errors = new HashMap<>();
	// ex.getBindingResult().getAllErrors().forEach(error -> {
	// String fieldName = ((FieldError) error).getField();
	// String errorMessage = error.getDefaultMessage();
	// errors.put(fieldName, errorMessage);
	// });
	//
	// return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	// }

}
