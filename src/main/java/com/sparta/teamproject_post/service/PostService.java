package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.CreatePostRequestDto;
import com.sparta.teamproject_post.dto.PostResponseDto;
import com.sparta.teamproject_post.dto.StatusResponseDto;
import com.sparta.teamproject_post.dto.UpdatePostRequestDto;
import com.sparta.teamproject_post.entity.Post;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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


    public List<PostResponseDto> readAllPost() {
        List<Post> posts = postRepository.findAll(); //데이터 다 꺼냄
        List<PostResponseDto> postResponsDtos = new ArrayList<>();// response를 만들어줌
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostResponseDto postResponseDto = new PostResponseDto(post.getId(), post.getTitle(), post.getPostContent(), post.getUserName(), post.getCreatedAt(), post.getModifiedAt());
            postResponsDtos.add(postResponseDto);
        }
        return postResponsDtos;
    }

    public PostResponseDto readPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return new PostResponseDto(post.getId(), post.getTitle(), post.getPostContent(), post.getUserName(), post.getCreatedAt(), post.getModifiedAt());
        }//예외처리 해주고
        return null;//예외처리 후 제거
    }
}
