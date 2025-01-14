package com.spring.boot.blog.service;

import com.spring.boot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getCommentsByPostId(long postId);

	CommentDto getCommentById(long postId, long commentId);

	CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

	void deleteCommentById(long postId, long commentId);

}
