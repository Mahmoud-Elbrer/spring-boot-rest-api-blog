package com.spring.boot.blog.service.impl;

import com.spring.boot.blog.entity.Category;
import com.spring.boot.blog.entity.Post;
import com.spring.boot.blog.excpetion.ResourceNotFoundException;
import com.spring.boot.blog.payload.PostDto;
import com.spring.boot.blog.payload.PostResponse;
import com.spring.boot.blog.repository.CategoryRepository;
import com.spring.boot.blog.repository.PostRepository;
import com.spring.boot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper mapper;

    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // get category details
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert Dto to Entity Posts
        Post post = mapToEntity(postDto);

        // set category to post entity before saving to database
        post.setCategory(category);

        // save post to database
        Post newPost = postRepository.save(post);

        // this method to convert Entity Post to Dto and return it
        return mapToDTO(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        // return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(@PathVariable("id") Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // get Post details
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // get category details
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post); // or can use deleteById
    }

    @Override
    public PostResponse getPaginationPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // Get content form page object
        List<Post> pageContent = posts.getContent();

        // Get the content of the page with Response
        List<PostDto> content = pageContent.stream().map(this::mapToDTO).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getPageable().getPageNumber());
        postResponse.setPageSize(posts.getPageable().getPageSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());

    }

    // convert Entity to Dto
    // convert Entity to Dto
    private PostDto mapToDTO(Post post) {
        return mapper.map(post, PostDto.class);
//        PostDto postDto = mapper.map(post, PostDto.class);
//        if (post.getCategory() != null) {
//            postDto.setCategoryId(post.getCategory().getId());
        //        }
        //     return postDto;
    }

    // convert Dto to Entity Posts
    private Post mapToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

}
