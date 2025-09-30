package com.bhngupta.TaskManager.auth.service;

import com.bhngupta.TaskManager.auth.model.User;
import com.bhngupta.TaskManager.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOrCreate(String email, String name, String picture) {
        Optional<User> opt = userRepository.findByEmail(email);
        return opt.orElseGet(() -> {
            User u = new User();
            u.setEmail(email);
            u.setName(name);
            u.setPicture(picture);
            return userRepository.save(u);
        });
    }
}