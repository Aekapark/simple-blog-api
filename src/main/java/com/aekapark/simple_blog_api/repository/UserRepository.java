package com.aekapark.simple_blog_api.repository;

import com.aekapark.simple_blog_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByUsername(String Usernames);

    Optional<User>findByEmail(String Email);
}

