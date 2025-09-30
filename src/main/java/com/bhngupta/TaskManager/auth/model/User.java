package com.bhngupta.TaskManager.auth.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	private String name;
	private String picture;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getPicture() { return picture; }
	public void setPicture(String picture) { this.picture = picture; }

}