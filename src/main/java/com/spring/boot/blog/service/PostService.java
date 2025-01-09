package com.spring.boot.blog.service;

import com.spring.boot.blog.payload.PostDto;
import com.spring.boot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPosts();

	PostDto getPostById(Long id);

	PostDto updatePost(PostDto postDto, Long id);

	void deletePost(Long id);

	PostResponse getPaginationPosts(int pageNo, int pageSize, String sortBy, String sortDirection);

	List<PostDto> getPostsByCategory(Long categoryId) ;

}
