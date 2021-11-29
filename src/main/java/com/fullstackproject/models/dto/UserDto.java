package com.fullstackproject.models.dto;

import com.fullstackproject.models.Joke;
import com.fullstackproject.models.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class UserDto {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdDate = LocalDateTime.now();
    private Set<Role> authorities;
    private List<Joke> joke;
    public UserDto() {
    }

    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", authorities=" + authorities +
                ", joke=" + joke +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public UserDto setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public UserDto setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
        return this;
    }

    public List<Joke> getJoke() {
        return joke;
    }

    public UserDto setJoke(List<Joke> joke) {
        this.joke = joke;
        return this;
    }
}
