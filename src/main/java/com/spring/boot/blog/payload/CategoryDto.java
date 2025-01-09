package com.spring.boot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty or null")
    private String name;

    @NotEmpty(message = "Description should not be empty or null")
    private String description;
}
