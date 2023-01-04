package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.CommentRequestdto;
import com.sparta.teamproject_post.dto.CommentResponseDto;
import com.sparta.teamproject_post.entity.Comment;
import com.sparta.teamproject_post.jwt.JwtUtil;
import com.sparta.teamproject_post.repository.CommentRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public final JwtUtil jwtUtil;

    @Transactional
    // 댓글 작성
    public CommentResponseDto createComment(Long id, CommentRequestdto requestdto, Claims claims) {
        // 먼저 Long id를 이용해서 id가 있는지 확인합니다.

        // 그다음 post가 있는지 확인합니다.


        // 둘 다 있으면 comment에 값을 담아주고 Repository에 save 함수를 이용해서 comment 를 넣어줍니다.
        // 그 후 ReponseDto에 comment를 넣어주고 리턴시킵니다.
        Comment comment = new Comment(requestdto,user,post);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 댓글 수정 기능
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestdto requestdto, Claims claims) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 올바르지 않습니다.")
        );

        // user가 맞는지 확인합니다. claims 사용


        comment.update(requestdto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id, Claims claims) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 올바르지 않습니다.")
        );

        // user가 맞는지 확인합니다. claims 사용

       commentRepository.deleteById(id);
    }
}