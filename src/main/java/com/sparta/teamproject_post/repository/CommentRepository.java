package com.sparta.teamproject_post.repository;

import com.sparta.teamproject_post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();

}
