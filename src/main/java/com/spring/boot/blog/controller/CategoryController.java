package com.spring.boot.blog.controller;

import com.spring.boot.blog.payload.CategoryDto;
import com.spring.boot.blog.service.CategoryService;
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
import java.util.Objects;

@RestController
@RequestMapping("/categories")
@Tag(
        name = "Categories",
        description = "Operations related to blog categories"
)
public class CategoryController {

    CategoryService categoryService;

    // Constructor injection
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Build Post new category REST API

    @Operation(
            summary = "Create a new category",
            description = "Create a new blog category with required fields"
    )
    @ApiResponse(
            responseCode = "201",
            description = "New category created successfully"  // Description for successful creation of a new category
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }


    // Build All Categories REST API

    @Operation(
            summary = "Get all categories",
            description = "Get all blog categories"
    )
    @ApiResponse(
            responseCode = "200",
            description = "All blog categories retrieved successfully"  // Description for successful retrieval of all categories
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    // Build Get Category By Id REST API
    @Operation(
            summary = "Get category by ID",
            description = "Get a blog category by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog category retrieved successfully by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    // Build update Category REST API
    @Operation(
            summary = "Update category by ID",
            description = "Update a blog category by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog category updated successfully by ID"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable(name = "id") Long categoryId) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, categoryId), HttpStatus.OK);
    }


    // Build delete Category REST API
    @Operation(
            summary = "Delete category by ID",
            description = "Delete a blog category by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Blog category deleted successfully by ID"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category Deleted Successfully", HttpStatus.OK);
    }
}
