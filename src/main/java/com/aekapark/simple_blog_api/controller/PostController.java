package com.aekapark.simple_blog_api.controller;

import com.aekapark.simple_blog_api.dto.PostRequest;
import com.aekapark.simple_blog_api.model.Post;
import com.aekapark.simple_blog_api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService pS){
        this.postService = pS;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());

        try {
            Post createdPost = postService.createPost(post,postRequest.getUserId());
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPost();
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable  Long id){
        Optional<Post> post = postService.getPostById(id);
        return post.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest){
        Post postDetails = new Post ();
        postDetails.setContent(postRequest.getContent());
        postDetails.setTitle(postRequest.getTitle());

        try {
            Post updatePost = postService.updatePost(id,postDetails);
            return new ResponseEntity<>(updatePost,HttpStatus.OK);

        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}") // Endpoint สำหรับดึงโพสต์ตามผู้ใช้งาน
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        try {
            List<Post> posts = postService.getPostByUserId(userId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // ถ้าไม่พบ user
        }
    }




}
