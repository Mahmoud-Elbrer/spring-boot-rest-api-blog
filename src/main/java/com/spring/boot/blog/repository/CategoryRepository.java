package com.spring.boot.blog.repository;

import com.spring.boot.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category ,Long> {
}
