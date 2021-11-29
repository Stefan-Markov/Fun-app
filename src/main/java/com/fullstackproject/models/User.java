package com.fullstackproject.models;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    private String username;
    private String email;
    private String password;
    private LocalDateTime createdDate = LocalDateTime.now();
    private Set<Role> authorities;
    private List<Joke> joke;

    @OneToMany(mappedBy = "user")
    public List<Joke> getJoke() {
        return joke;
    }

    public User setJoke(List<Joke> joke) {
        this.joke = joke;
        return this;
    }

    @Column(name = "email")
    @Email(message = "Enter valid email!")
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(name = "created_info_user", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public User setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Column(name = "username", nullable = false, unique = true)
    @Length(min = 5, message = "Username must be between five and fifty symbols")
    @NotNull(message = "Username has to be filled!")
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    @Column(name = "password")
    @Length(min = 5, message = "Password must be between five and fifty symbols")
    @NotNull(message = "Password has to be filled!")
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

}
