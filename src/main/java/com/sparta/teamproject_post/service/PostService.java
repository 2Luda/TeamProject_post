package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.*;
import com.sparta.teamproject_post.entity.Comment;
import com.sparta.teamproject_post.entity.Post;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.CommentRepository;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final Jwtutil jwtutil;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public StatusResponseDto createPost(CreatePostRequestDto createPostRequestDto, Claims claims) {

        Optional<User> optionalUser = userRepository.findByUsername(claims.getSubject());
        if (!optionalUser.isPresent()){
            return new StatusResponseDto("사용자가 존재하지 않습니다.", 400);
        }
        User user = optionalUser.get();
        Post post = new Post(createPostRequestDto.getTitle(), user.getUsername(), createPostRequestDto.getPostContent());
        postRepository.save(post);
        return new StatusResponseDto("게시글이 작성 되었습니다.", 200);
    }

    public StatusResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Claims claims) {

        Optional<User> optionalUser = userRepository.findByUsername(claims.getSubject());
        if (!optionalUser.isPresent()){
            return new StatusResponseDto("사용자가 존재하지 않습니다.", 400);
        }
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            return new StatusResponseDto("게시글이 존재하지 않습니다.", 400);
            //예외처리
        }
        User user = optionalUser.get();
        Post post = optionalPost.get();

        if (user.getUsername().equals(post.getUserName())) {

            post.update(updatePostRequestDto.getTitle(), updatePostRequestDto.getPostContent());
            postRepository.save(post);
            return new StatusResponseDto("게시글이 수정 되었습니다.", 200);
        }else {
            return new StatusResponseDto("권한이 없습니다.", 401);
        }
    }

    public StatusResponseDto deletePost(Long postId, Claims claims) {
        Optional<User> optionalUser = userRepository.findByUsername(claims.getSubject());
        if (!optionalUser.isPresent()){
            return new StatusResponseDto("사용자가 존재하지 않습니다.", 400);
        }
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            return new StatusResponseDto("게시글이 존재하지 않습니다.", 400);
        }
        User user = optionalUser.get();
        Post post = optionalPost.get();

        if (user.getUsername().equals(post.getUserName()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.delete(post);
            return new StatusResponseDto("게시글이 삭제되었습니다.", 200);
        }else {
            return new StatusResponseDto("권한이 없습니다.", 401);
        }
    }

    //전체 게시판 조회
    public List<PostResponseDto> readAllPost() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.ASC,"CreatedAt")); //데이터 다 꺼냄
        List<PostResponseDto> postResponsDtos = new ArrayList<>();// response를 만들어줌
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            List<Comment> comments = commentRepository.findAllByPostId(post.getId());
            for (Comment comment : comments) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
                commentResponseDtos.add(commentResponseDto);
            }
            PostResponseDto postResponseDto = new PostResponseDto(post.getId(), post.getTitle(), post.getPostContent(), post.getUserName(), post.getCreatedAt(), post.getModifiedAt(),commentResponseDtos);
            postResponsDtos.add(postResponseDto);
        }
        return postResponsDtos;
    }

    public PostResponseDto readPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            List<Comment> comments = commentRepository.findAllByPostId(post.getId());
            for (Comment comment : comments) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
                commentResponseDtos.add(commentResponseDto);
            }
            return new PostResponseDto(post.getId(), post.getTitle(), post.getPostContent(), post.getUserName(), post.getCreatedAt(), post.getModifiedAt(),commentResponseDtos);
        }else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
    }
}
