package com.sparta.teamproject_post.dto;

import com.sparta.teamproject_post.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDto {

    // 응답을 보낼 데이터 중 어떤 데이터를 보여줄지 선택해서 필드에 넣는다.
    // postman에 댓글 출력이 될때 id와 username, comment, 생성시간, 수정시간이 출력되도록 만들었습니다.
    private Long Id;
    private String username;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Comment comment 를 매개변수로 받는 CommentResponseDto를 만들었습니다.
    // id, username, comment, createdAt, modifiedAt은 comment(Entity)에서 가져오도록 만들었습니다.
    public CommentResponseDto(Comment comment) {
        this.Id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
