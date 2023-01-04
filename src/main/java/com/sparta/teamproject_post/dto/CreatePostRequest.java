package com.sparta.teamproject_post.dto;

import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title;
    private String userName;
    private String postContent;
}
