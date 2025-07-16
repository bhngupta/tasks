package com.bhngupta.tasks;

import com.bhngupta.tasks.auth.dto.AuthRequest;
import com.bhngupta.tasks.auth.dto.AuthResponse;
import com.bhngupta.tasks.auth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAccessTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;

    private final String email = "jwt-secure@kanban.com";
    private final String password = "securepass";

    private String token;

    @BeforeEach
    void registerAndLogin() throws Exception {
        userRepository.deleteByEmail(email);

        AuthRequest req = new AuthRequest(email, password);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)));

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andReturn();

        String json = result.getResponse().getContentAsString();
        token = objectMapper.readValue(json, AuthResponse.class).getToken();
    }

    @Test
    void accessProtectedEndpointWithToken() throws Exception {
        mockMvc.perform(get("/api/user/me")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().string("authenticated"));
    }

    @Test
    void accessProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/user/me"))
            .andExpect(status().isUnauthorized());
    }
}
