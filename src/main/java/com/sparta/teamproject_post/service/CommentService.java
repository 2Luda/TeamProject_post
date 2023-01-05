package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.CommentRequestDto;
;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.entity.Comment;
import com.sparta.teamproject_post.entity.Post;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.repository.CommentRepository;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    // 댓글 작성
    public StatusResponseDto createComment(Long id, CommentRequestDto requestdto, User user) {
        // 먼저 Long id를 이용해서 id가 있는지 확인합니다.
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()){
            return new StatusResponseDto("게시글을 찾을 수 없습니다.", 400);
        }
        Post post = optionalPost.get();
        Comment comment = new Comment(user, requestdto.getComment(), post);
        commentRepository.save(comment);
        return new StatusResponseDto("댓글을 작성했습니다.",200);
    }

    // 댓글 수정 기능
    @Transactional
    public StatusResponseDto updateComment(Long id, CommentRequestDto requestdto, User user) {

        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()){
            return new StatusResponseDto("댓글을 찾을 수 없습니다.", 400);
        }
        // user가 맞는지 확인합니다.
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()){
            return new StatusResponseDto("사용자를 찾을 수 없습니다.",400);
        }
        Comment comment = optionalComment.get();

        // USER가 회원이면 작성한 게시글/댓글 수정 가능 / 어드민은 모든 댓글 수정 가능
        if (user.getUsername().equals(comment.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.update(requestdto.getComment());
            return new StatusResponseDto("댓글을 수정했습니다.",200);
        }else {
            return new StatusResponseDto("권한이 없습니다.",401);
        }

    }

    // 댓글 삭제 기능
    public StatusResponseDto deleteComment(Long id, User user) {

        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()){
            return new StatusResponseDto("댓글을 찾을 수 없습니다.",400);
        }


        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalComment.isPresent()){
            return new StatusResponseDto("사용자를 찾을 수 없습니다.",400);
        }
        Comment comment = optionalComment.get();
        // USER가 회원이면 작성한 게시글/댓글 삭제 가능 / 어드민은 모든 게시글/댓글 삭제 가능 (미구현)
        if (user.getUsername().equals(comment.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.delete(comment);
            return new StatusResponseDto("댓글을 삭제했습니다.",200);
        }else {
            return new StatusResponseDto("권한이 없습니다.",401);
        }
    }
}
