package com.sparta.teamproject_post.controller;

import com.sparta.teamproject_post.dto.CreatePostRequest;
import com.sparta.teamproject_post.dto.PostResponse;
import com.sparta.teamproject_post.dto.UpdatePostRequest;
import com.sparta.teamproject_post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(@RequestBody PostService postService){
        this.postService = postService;
    }


    @PostMapping("/api/posts")       //게시물 생성
    public void createPost(@RequestBody CreatePostRequest createPostRequest){
        postService.createPost(createPostRequest);
    }

    @PutMapping("/api/posts/{id}")
    public void updatePost(@PathVariable Long id, UpdatePostRequest updatePostRequest){
        postService.updatePost(id, updatePostRequest);
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }

    @GetMapping("/api/posts")
    @ResponseBody
    public List<PostResponse> readAllPost(){
        return postService.readAllPost();
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    public PostResponse readPost(@PathVariable Long id){
        return postService.readPost(id);
    }


}
