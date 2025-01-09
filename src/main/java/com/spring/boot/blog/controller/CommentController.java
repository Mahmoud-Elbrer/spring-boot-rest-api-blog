package com.spring.boot.blog.controller;

import com.spring.boot.blog.payload.CommentDto;
import com.spring.boot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService1) {
		this.commentService = commentService1;
	}

	@SecurityRequirement(
			name = "Bearer Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@GetMapping("/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId) {
		return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
	}

	@GetMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId) {
		return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}

	@PutMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId, @Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
	}

	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId) {
		commentService.deleteCommentById(postId, commentId);
		return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
	}

}