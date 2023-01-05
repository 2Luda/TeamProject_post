package com.sparta.teamproject_post.repository;

import com.sparta.teamproject_post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}
