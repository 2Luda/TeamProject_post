package com.sparta.teamproject_post.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String postContent;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Long id, String title, String postContent, String userName, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponseDto> comments) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.userName = userName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}
