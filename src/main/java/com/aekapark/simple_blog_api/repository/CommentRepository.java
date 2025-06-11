package com.aekapark.simple_blog_api.repository;

import com.aekapark.simple_blog_api.model.Comment;
import com.aekapark.simple_blog_api.model.Post;
import com.aekapark.simple_blog_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);

    List<Comment> findByPostAndUser(Post post, User user);
}
