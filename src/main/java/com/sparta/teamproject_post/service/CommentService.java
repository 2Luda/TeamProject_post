package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.CommentRequestdto;
import com.sparta.teamproject_post.dto.CommentResponseDto;
import com.sparta.teamproject_post.entity.Comment;
import com.sparta.teamproject_post.entity.Post;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.repository.CommentRepository;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    // 댓글 작성
    public CommentResponseDto createComment(Long id, CommentRequestdto requestdto, Claims claims) {
        // 먼저 Long id를 이용해서 id가 있는지 확인합니다.
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("타인의 댓글은 수정 할 수 없습니다.")
        );

        // 그다음 토큰이 유효한지 확인합니다.
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("타인의 댓글에 작성 할 수 없습니다.")
        );


        // 둘 다 있으면 comment에 값을 담아주고 Repository에 save 함수를 이용해서 comment 를 넣어줍니다.
        // 그 후 ReponseDto에 comment를 넣어주고 리턴시킵니다.
        Comment comment = new Comment(user, requestdto, post);
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
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("타인의 댓글은 수정 할 수 없습니다.")
        );

        // USER가 회원이면 작성한 게시글/댓글 수정 가능 / 어드민은 모든 게시글/댓글 수정 가능 (미구현)

        comment.update(requestdto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제 기능
    public void deleteComment(Long id, Claims claims) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 올바르지 않습니다.")
        );

        // user가 맞는지 확인합니다. claims 사용
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("타인의 댓글은 수정 할 수 없습니다.")
        );

        // USER가 회원이면 작성한 게시글/댓글 삭제 가능 / 어드민은 모든 게시글/댓글 삭제 가능 (미구현)

        commentRepository.deleteById(id);
    }
}
