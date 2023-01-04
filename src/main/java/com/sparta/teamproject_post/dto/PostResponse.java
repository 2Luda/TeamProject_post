package com.sparta.teamproject_post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String postContent;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponse(Long id, String title, String postContent, String userName, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.userName = userName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
