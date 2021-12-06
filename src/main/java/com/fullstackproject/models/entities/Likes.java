package com.fullstackproject.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes_of_joke")
public class Likes extends BaseEntity {

    private Joke joke;
    private String ownerOfComment;

    @ManyToOne
    @JsonIgnore
    public Joke getJoke() {
        return joke;
    }

    public Likes setJoke(Joke joke) {
        this.joke = joke;
        return this;
    }

    @Column(name = "owner_of_like")
    public String getOwnerOfComment() {
        return ownerOfComment;
    }

    public Likes setOwnerOfComment(String ownerOfComment) {
        this.ownerOfComment = ownerOfComment;
        return this;
    }
}
