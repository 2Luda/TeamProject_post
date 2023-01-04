package com.sparta.teamproject_post.service;

import com.sparta.teamproject_post.dto.CreatePostRequest;
import com.sparta.teamproject_post.dto.PostResponse;
import com.sparta.teamproject_post.dto.UpdatePostRequest;
import com.sparta.teamproject_post.entity.Post;
import com.sparta.teamproject_post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(CreatePostRequest createPostRequest) {
        Post post = new Post(createPostRequest.getTitle(), createPostRequest.getUserName(), createPostRequest.getPostContent());
        postRepository.save(post);
    }

    public void updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.update(updatePostRequest.getTitle(), updatePostRequest.getPostContent());
            postRepository.save(post);
        }//예외처리 추가해주기.
    }

    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
        }
    }

    public List<PostResponse> readAllPost(){
        List<Post> posts= postRepository.findAll(); //데이터 다 꺼냄
        List<PostResponse> postResponses = new ArrayList<>();// response를 만들어줌
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostResponse postResponse = new PostResponse(post.getId(),post.getTitle(), post.getPostContent(), post.getUserName(), post.getcreatedAt(),post.getmodifiedAt());
            postResponses.add(postResponse);
        }
        return postResponses;
    }

    public PostResponse readPost(Long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            return new PostResponse(post.getId(),post.getTitle(), post.getPostContent(), post.getUserName(), post.getcreatedAt(),post.getmodifiedAt());
        }//예외처리 해주고
        return null;//예외처리 후 제거
    }
}
