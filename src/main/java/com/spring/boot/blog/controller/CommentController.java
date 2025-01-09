package com.spring.boot.blog.controller;

import com.spring.boot.blog.payload.CommentDto;
import com.spring.boot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(
        name = "Comments",
        description = "Operations related to blog comments"
)
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService1) {
        this.commentService = commentService1;
    }


    @Operation(
            summary = "Create a new comment",
            description = "Create a new comment for a post with required fields"
    )
    @ApiResponse(
            responseCode = "201",
            description = "New comment created successfully"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get a comment by Post ID",
            description = "Get a comment by Post ID for a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment retrieved successfully"
    )
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get a comment by ID",
            description = "Get a comment by ID for a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment retrieved successfully"
    )
    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "commentId") long commentId) {
        return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }


    @Operation(
            summary = "Update a comment",
            description = "Update a comment for a post with required fields"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment updated successfully"
    )
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "commentId") long commentId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a comment",
            description = "Delete a comment by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment deleted successfully"
    )
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentId") long commentId) {
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }

}