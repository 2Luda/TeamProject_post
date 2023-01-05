package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.CreatePostRequestDto;
import com.sparta.teamproject_post.dto.PostResponse;
import com.sparta.teamproject_post.dto.UpdatePostRequest;
import com.sparta.teamproject_post.entity.Post;
import com.sparta.teamproject_post.entity.User;
import com.sparta.teamproject_post.entity.UserRoleEnum;
import com.sparta.teamproject_post.jwt.Jwtutil;
import com.sparta.teamproject_post.repository.PostRepository;
import com.sparta.teamproject_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final Jwtutil jwtutil;
    private final PostRepository postRepository;


    public void createPost(CreatePostRequestDto createPostRequestDto, Claims claims) {

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Post post = new Post(createPostRequestDto.getTitle(), user.getUsername(), createPostRequestDto.getPostContent());
        postRepository.save(post);

    }

    public void updatePost(Long postId, UpdatePostRequest updatePostRequest, Claims claims) {

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
            //예외처리
        }
        Post post = optionalPost.get();

        if (user.getUsername().equals(post.getUserName())) {

            post.update(updatePostRequest.getTitle(), updatePostRequest.getPostContent());
            postRepository.save(post);
        }else {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }
    }

    public void deletePost(Long postId, Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
        Post post = optionalPost.get();

        if (user.getUsername().equals(post.getUserName()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.delete(post);
        }else {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }
    }


    public List<PostResponse> readAllPost() {
        List<Post> posts = postRepository.findAll(); //데이터 다 꺼냄
        List<PostResponse> postResponses = new ArrayList<>();// response를 만들어줌
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostResponse postResponse = new PostResponse(post.getId(), post.getTitle(), post.getPostContent(), post.getUserName(), post.getCreatedAt(), post.getModifiedAt());
            postResponses.add(postResponse);
        }
        return postResponses;
    }

    public PostResponse readPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return new PostResponse(post.getId(), post.getTitle(), post.getPostContent(), post.getUserName(), post.getCreatedAt(), post.getModifiedAt());
        }//예외처리 해주고
        return null;//예외처리 후 제거
    }
}
