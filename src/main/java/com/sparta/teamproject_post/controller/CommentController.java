package com.sparta.teamproject_post.controller;
import com.sparta.teamproject_post.dto.CommentRequestDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.security.UserDetailsImpl;
import com.sparta.teamproject_post.service.CommentService;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/posts/{id}/comments")
    public StatusResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestdto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id,requestdto,userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/api/comments/{id}")
    public StatusResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestdto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id,requestdto,userDetails.getUser());
    }

    // 댓글 삭제
    @ResponseBody
    @DeleteMapping("/api/comments/{id}")
    public StatusResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id,userDetails.getUser());
    }




}
