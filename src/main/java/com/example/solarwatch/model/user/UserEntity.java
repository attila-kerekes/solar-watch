package com.example.solarwatch.model.user;

import jakarta.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long user_id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

