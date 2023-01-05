package com.sparta.teamproject_post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //long id = 0L;  //객체를 생성할때 id에 0을 넣고 시작
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String postContent;

    public Post(String title, String userName, String postContent) {
        this.title = title;
        this.userName = userName;
        this.postContent = postContent;
    }

    //게시글 수정
    public void update(String title, String postContent) {
        this.title = title;
        this.postContent = postContent;
    }

    //페이지 삭제시 좋아요 같이 삭제

}
