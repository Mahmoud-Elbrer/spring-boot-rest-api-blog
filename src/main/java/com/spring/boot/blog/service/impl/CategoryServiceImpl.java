package com.spring.boot.blog.service.impl;

import com.spring.boot.blog.entity.Category;
import com.spring.boot.blog.entity.Post;
import com.spring.boot.blog.excpetion.ResourceNotFoundException;
import com.spring.boot.blog.payload.CategoryDto;
import com.spring.boot.blog.payload.PostDto;
import com.spring.boot.blog.repository.CategoryRepository;
import com.spring.boot.blog.service.CategoryService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = mapToEntity(categoryDto);

        Category newCategory = categoryRepository.save(category);

        return mapToDto(newCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        ;

        return mapToDto(category);
    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return mapToDto(categoryRepository.save(category));
    }


    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

    }

    private Category mapToEntity(CategoryDto categoryDto) {
        return mapper.map(categoryDto, Category.class);
    }

    private CategoryDto mapToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }

}
