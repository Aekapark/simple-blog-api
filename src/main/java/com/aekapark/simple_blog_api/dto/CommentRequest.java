package com.aekapark.simple_blog_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Post ID is required")
    private Long postId;
}
