package com.sparta.teamproject_post.dto;

import lombok.Getter;

@Getter
public class StatusResponseDto {
    private String msg;
    private int statusCode;

    public StatusResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
