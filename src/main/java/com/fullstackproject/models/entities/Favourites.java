package com.fullstackproject.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "favourites")
public class Favourites extends BaseEntity {

    private String username;
    private String jokeId;

    public Favourites() {
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public Favourites setUsername(String username) {
        this.username = username;
        return this;
    }
    @Column(name = "joke_id")
    public String getJokeId() {
        return jokeId;
    }

    public Favourites setJokeId(String jokeId) {
        this.jokeId = jokeId;
        return this;
    }
}
