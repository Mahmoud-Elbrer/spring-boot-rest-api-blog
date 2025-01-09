package com.spring.boot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id ;

    @Column(name = "name" , nullable = false , unique = true)
    private String name ;

    @Column(name = "description", nullable = false )
    private String description ;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
    //private Set<Post> posts = new HashSet<>();
}
