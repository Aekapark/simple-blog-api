package com.aekapark.simple_blog_api.service;

import com.aekapark.simple_blog_api.model.Comment;
import com.aekapark.simple_blog_api.model.Post;
import com.aekapark.simple_blog_api.model.User;
import com.aekapark.simple_blog_api.repository.CommentRepository;
import com.aekapark.simple_blog_api.repository.PostRepository;
import com.aekapark.simple_blog_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository cp,PostRepository pr,UserRepository ur){
        this.commentRepository = cp;
        this.postRepository = pr;
        this.userRepository = ur;
    }

    @Transactional
    public Comment createComment(Comment comment, Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found with id = " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found with id = " +postId));

        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment updateComment(Long id, Comment commentDetails){
        return commentRepository.findById(id)
                .map(exitingComment -> {
                    exitingComment.setContent(commentDetails.getContent());
                    return commentRepository.save(exitingComment);
                }).orElseThrow(() -> new IllegalArgumentException("Comment not found by id = " + id));
    }

    @Transactional
    public void deleteComment(Long id){
        if(commentRepository.existsById(id) == false){
            throw new IllegalArgumentException("commnet not found by id =" +id );
        }

        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id " + postId));
        return commentRepository.findByPost(post);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
        return commentRepository.findByUser(user);
    }
}
