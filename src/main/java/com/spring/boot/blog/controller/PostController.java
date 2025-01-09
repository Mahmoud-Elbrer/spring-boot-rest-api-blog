package com.spring.boot.blog.controller;

import com.spring.boot.blog.payload.PostDto;
import com.spring.boot.blog.payload.PostResponse;
import com.spring.boot.blog.service.PostService;
import com.spring.boot.blog.utils.AppConstants;
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
@RequestMapping("/post")
@Tag(
        name = "Posts",
        description = "Operations related to blog posts"
)
public class PostController {

    // Controller methods for handling posts
    private final PostService postService;

    // constructor injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post rest api
    @Operation(
            summary = "Create a new blog post",
            description = "Create a new blog post with required fields"
    )
    @ApiResponse(
            responseCode = "201",
            description = "New blog post created successfully"
    )
    @SecurityRequirement(
            // "Bearer Authentication" must be same name in SecurityConfig in SecurityScheme
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post) {
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get All Posts",
            description = "Get all posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog posts retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {

        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);

    }

    @Operation(
            summary = "Get Paging Posts",
            description = "Get Posts By PageNo , PageSize , SortBy and sortDirection"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog posts retrieved successfully"
    )
    @GetMapping("/paging")
    public ResponseEntity<PostResponse> getPaginationPosts(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDirection) {

        return new ResponseEntity<>(postService.getPaginationPosts(pageNo, pageSize, sortBy, sortDirection),
                HttpStatus.OK);

    }


    @Operation(
            summary = "Get Post By ID",
            description = "Get a blog post by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog post retrieved successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
        // this other way return ResponseEntity.ok(postService.getPostById(id));
    }


    @Operation(
            summary = "Update Post",
            description = "Update a blog post by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog post updated successfully"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Post",
            description = "Delete a blog post by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog post deleted successfully"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }


    // Build Get Posts By Category REST API
    @Operation(
            summary = "Get Posts By Category",
            description = "Get all blog posts by category ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog posts retrieved successfully by category"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostsByCategory(id), HttpStatus.OK);
    }


}
