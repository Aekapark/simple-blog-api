package com.aekapark.simple_blog_api.service;

import com.aekapark.simple_blog_api.model.User;
import com.aekapark.simple_blog_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user){

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already exits");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email is already exits");
        }

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword()); // ควรมีการเข้ารหัส password ก่อน
                    return userRepository.save(user);
                }).orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
    }

    @Transactional
    public void deleteUser(Long id){
        if(userRepository.existsById(id) == false) {
            throw new IllegalArgumentException("User not found = " + id);
        }
        userRepository.deleteById(id);
    }

}
