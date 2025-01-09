package com.spring.boot.blog.service;

import com.spring.boot.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory( CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long categoryId);


    CategoryDto updateCategory(CategoryDto category , Long categoryId) ;

    void deleteCategory(Long categoryId);

}
