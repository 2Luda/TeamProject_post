package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.CreatePostRequestDto;
import com.sparta.teamproject_post.dto.PostResponse;
import com.sparta.teamproject_post.dto.UpdatePostRequest;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final Jwtutil jwtUtil;
    private final PostRepository postRepository;
    private final PostService postService;


    @PostMapping("/api/posts")       //게시물 생성
    public void createPost(@RequestBody CreatePostRequestDto createPostRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            postService.createPost(createPostRequestDto, claims);
        } else {
            throw new IllegalArgumentException("작성 실패");
        }


    }

    @PutMapping("/api/posts/{id}")
    public void updatePost(@PathVariable Long id, UpdatePostRequest updatePostRequest, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            postService.updatePost(id, updatePostRequest, claims);
        } else {
            throw new IllegalArgumentException("작성 실패");
        }

    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            postService.deletePost(id, claims);
        } else {
            throw new IllegalArgumentException("작성 실패");
        }
    }

    @GetMapping("/api/posts")
    @ResponseBody
    public List<PostResponse> readAllPost() {
        return postService.readAllPost();
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponse readPost(@PathVariable Long id) {
        return postService.readPost(id);
    }


}
