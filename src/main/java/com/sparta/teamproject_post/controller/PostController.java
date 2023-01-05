package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.CreatePostRequestDto;
import com.sparta.teamproject_post.dto.PostResponseDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.dto.UpdatePostRequestDto;
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


    @ResponseBody
    @PostMapping("/api/posts")       //게시물 생성
    public StatusResponseDto createPost(@RequestBody CreatePostRequestDto createPostRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return new StatusResponseDto("token이 올바르지 않습니다.", 400);
            }
            return postService.createPost(createPostRequestDto, claims);
        } else {
            return new StatusResponseDto("token이 없습니다.", 400);
        }


    }

    @ResponseBody
    @PutMapping("/api/posts/{id}")
    public StatusResponseDto updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return new StatusResponseDto("token이 올바르지 않습니다.", 400);
            }
            return postService.updatePost(id, updatePostRequestDto, claims);
        } else {
            return new StatusResponseDto("token이 없습니다.", 400);
        }

    }

    @ResponseBody
    @DeleteMapping("/api/posts/{id}")
    public StatusResponseDto deletePost(@PathVariable Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return new StatusResponseDto("token이 올바르지 않습니다.", 400);
            }
             return postService.deletePost(id, claims);
        } else {
            return new StatusResponseDto("token이 없습니다.", 400);
        }
    }

    @GetMapping("/api/posts")
    @ResponseBody
    public List<PostResponseDto> readAllPost() {
        return postService.readAllPost();
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponseDto readPost(@PathVariable Long id) {
        return postService.readPost(id);
    }


}
