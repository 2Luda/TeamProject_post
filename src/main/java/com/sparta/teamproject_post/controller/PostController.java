package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.CreatePostRequestDto;
import com.sparta.teamproject_post.dto.PostResponseDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.dto.UpdatePostRequestDto;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.security.UserDetailsImpl;
import com.sparta.teamproject_post.service.PostService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/api/posts") //게시물 생성
    public StatusResponseDto createPost(@RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.createPost(createPostRequestDto, userDetails.getUser());
    }

    @ResponseBody
    @PutMapping("/api/posts/{id}") // 게시물 수정
    public StatusResponseDto updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.updatePost(id, updatePostRequestDto, userDetails.getUser());
    }

    @ResponseBody
    @DeleteMapping("/api/posts/{id}") // 게시물 삭제
    public StatusResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

         return postService.deletePost(id, userDetails.getUser());

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
