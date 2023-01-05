package com.sparta.teamproject_post.controller;
import com.sparta.teamproject_post.dto.CommentRequestDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
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
    @PostMapping("/api/posts/{id}/comments")
    public StatusResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestdto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return new StatusResponseDto("token이 유효하지 않습니다.",400);
            }
            return commentService.createComment(id,requestdto,claims);
        } else {
            return new StatusResponseDto("token이 없습니다.",400);
        }
    }

    // 댓글 수정
    @PutMapping("/api/comments/{id}")
    public StatusResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestdto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return new StatusResponseDto("token이 유효하지 않습니다.",400);
            }
            return commentService.updateComment(id,requestdto,claims);
        } else {
            return new StatusResponseDto("token이 없습니다.",400);
        }
    }

    // 댓글 삭제
    @ResponseBody
    @DeleteMapping("/api/comments/{id}")
    public StatusResponseDto deleteComment(@PathVariable Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return new StatusResponseDto("token이 유효하지 않습니다.",400);
            }

            return commentService.deleteComment(id,claims);
        }else {
            return new StatusResponseDto("token이 없습니다.",400);
        }
    }




}
