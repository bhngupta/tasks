package com.bhngupta.tasks.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users") // avoid 'user' keyword in PostgreSQL
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password; // hashed (only for local)

    private String name;

    private String provider; // "GOOGLE", "GITHUB", "LOCAL"

    private String role; // e.g. ROLE_USER, ROLE_ADMIN
}
