package com.sparta.teamproject_post.entity;

import com.sparta.teamproject_post.dto.CommentRequestdto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    @Column(nullable = false)
    public String comment;

    @Column(nullable = false)
    public String username;

    // 1개의 게시글에 여러개의 댓글을 남길 수 있다.
    // 그래서 ManyToOne 어노테이션을 사용하고 게시글을 불러와야 하므로 post를 만든다.
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    public Comment(User user, CommentRequestdto requestdto, Post post) {
        this.comment = requestdto.getComment();
        this.username = user.getUsername();
        this.post = post;
    }

    public void update(CommentRequestdto requestdto) {
        this.comment = requestdto.getComment();
    }
}
