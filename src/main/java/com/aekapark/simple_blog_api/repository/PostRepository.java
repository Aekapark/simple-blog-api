package com.aekapark.simple_blog_api.repository;

import com.aekapark.simple_blog_api.model.Post;
import com.aekapark.simple_blog_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByUser(User user)
}
