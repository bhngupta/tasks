package com.bhngupta.TaskManager.auth.controller;

import com.bhngupta.TaskManager.auth.dto.AuthResponse;
import com.bhngupta.TaskManager.auth.service.FBAuthService;
import com.google.firebase.auth.FirebaseToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final FBAuthService fbAuthService;
    private final ObjectMapper mapper = new ObjectMapper(); 

    public AuthController(FBAuthService fbAuthService) {
        this.fbAuthService = fbAuthService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Auth service is running");
    }

    @PostMapping("/verify-token")
    public ResponseEntity<AuthResponse> verifyToken(@RequestBody String idToken) {
        try {
            FirebaseToken decoded = fbAuthService.verifyIdToken(idToken);
            String decodedJson = mapper.writeValueAsString(decoded);
            return ResponseEntity.ok(new AuthResponse("Token valid", 200, decodedJson));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthResponse("Invalid token", 401, null));
        }
    }
}