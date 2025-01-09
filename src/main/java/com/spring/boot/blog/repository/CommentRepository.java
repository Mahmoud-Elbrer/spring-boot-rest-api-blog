package com.spring.boot.blog.repository;

import com.spring.boot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// this custom query method is mean
	// SELECT * FROM comment WHERE post_id= Id ;
	List<Comment> findByPostId(long Id);

}
