package com.fullstackproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "likes_of_comment")
public class Likes extends BaseEntity {

    private Integer count;
    private List<User> user;
    private Joke joke;

    @OneToMany(mappedBy = "likes")
    public List<User> getUser() {
        return user;
    }

    public Likes setUser(List<User> user) {
        this.user = user;
        return this;
    }

    @ManyToOne
    @JsonIgnore
    public Joke getJoke() {
        return joke;
    }

    public Likes setJoke(Joke joke) {
        this.joke = joke;
        return this;
    }

    @Column(name = "count", columnDefinition = "integer default 0")
    public Integer getCount() {
        return count;
    }

    public Likes setCount(Integer count) {
        this.count = count;
        return this;
    }

}
