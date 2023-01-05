package com.sparta.teamproject_post.controller;
import com.sparta.teamproject_post.dto.CommentRequestdto;
import com.sparta.teamproject_post.dto.CommentResponseDto;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.service.CommentService;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final Jwtutil jwtUtil;

    // 댓글 작성
    @PostMapping("/api/post/{id}/comment")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestdto requestdto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            return commentService.createComment(id,requestdto,claims);
        } else {
            throw new IllegalArgumentException("작성 실패");
        }
    }

    // 댓글 수정
    @PutMapping("/api/post/{id}/comment")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestdto requestdto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            return commentService.updateComment(id,requestdto,claims);
        } else {
            throw new IllegalArgumentException("조회 실패");
        }
    }

    // 댓글 삭제
    @DeleteMapping("/api/post/{id}/comment")
    public void deleteComment(@PathVariable Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            commentService.deleteComment(id,claims);
        }
    }




}
