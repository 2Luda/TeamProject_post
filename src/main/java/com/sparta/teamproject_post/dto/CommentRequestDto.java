package com.sparta.teamproject_post.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    // Postman에서 사용하는 dto ex) postman에서 값을 줄때 "comment" : "댓글"
    private String comment;
}
