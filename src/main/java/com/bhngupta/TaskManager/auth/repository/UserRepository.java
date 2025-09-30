package com.bhngupta.TaskManager.auth.repository;

import com.bhngupta.TaskManager.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}