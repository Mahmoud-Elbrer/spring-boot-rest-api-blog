package com.spring.boot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Schema(
        name = "Post",
        description = "Post object",
        requiredProperties = {"title", "description", "content", "categoryId"}
)
@Getter
@Setter
public class PostDto {

    private Long id;

    @Schema(
            description = "Post title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should hava at least 2 characters")
    private String title;

    @Schema(
            description = "Post description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @Schema(
            description = "Post content"
    )
    @NotEmpty
    private String content;

    @Schema(
            description = "Post comments"
    )
    private Set<CommentDto> comments;

    @Schema(
            description = "Post category ID"
    )
//	@NotEmpty(message = "Category ID should not be empty or null")
    private Long categoryId;

}
