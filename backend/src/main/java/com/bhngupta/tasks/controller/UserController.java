package com.bhngupta.tasks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<String> currentUser() {
        return ResponseEntity.ok("authenticated");
    }
}
