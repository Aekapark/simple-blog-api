package com.aekapark.simple_blog_api.service;

import com.aekapark.simple_blog_api.model.Post;
import com.aekapark.simple_blog_api.model.User;
import com.aekapark.simple_blog_api.repository.PostRepository;
import com.aekapark.simple_blog_api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Post createPost(Post post , long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id = " + userId));

        post.setUser(user);

        if(postRepository.findByTitleIgnoreCase(post.getTitle()).isPresent()){
            throw new IllegalArgumentException("Post with this title already exits");
        }

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    @Transactional
    public Post updatePost(Long id, Post postDetails){
        return postRepository.findById(id)
                .map( existingPost -> {
                    existingPost.setTitle(postDetails.getTitle());
                    existingPost.setContent(postDetails.getContent());
                    return postRepository.save(existingPost);
                }).orElseThrow(() -> new IllegalArgumentException("Post not found with id = " + id));
    }

    @Transactional
    public void deletePost(Long id){
        if(postRepository.existsById(id) == false){
            throw new IllegalArgumentException("Post not found with id = " + id);
        }

        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> getPostByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id = " + userId));
        return postRepository.findByUser(user);
    }

}
