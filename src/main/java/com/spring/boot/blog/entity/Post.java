package com.spring.boot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = "comments")
@EqualsAndHashCode(exclude = "comments")
@Builder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "posts")
// @Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "content")
	// @Column(name = "content", nullable = false)
	private String content;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
	private Category category ;

}
