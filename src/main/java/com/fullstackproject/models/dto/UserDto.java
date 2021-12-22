package com.fullstackproject.models.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.models.entities.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonPropertyOrder({"id", "username", "email", "authorities"})
public class UserDto {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdDate = LocalDateTime.now();
    private Set<Role> authorities;
    private List<Joke> joke;

    public UserDto() {
    }
    @JsonGetter("id")
    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        this.id = id;
        return this;
    }
    @JsonGetter("username")
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
    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }
    @JsonGetter("createdDate")
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

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, createdDate, authorities, joke);
    }
}
