package com.fullstackproject.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CommentDto {
    private String id;
    private String content;
    private String ownerOfComment;
    private String jokeId;

    public CommentDto() {
    }

    public String getId() {
        return id;
    }

    public CommentDto setId(String id) {
        this.id = id;
        return this;
    }

    @NotNull(message = "Please provide at least 2 symbols to comment")
    @Length(min = 2, message = "Must contains at least 2 symbols to comment")
    public String getContent() {
        return content;
    }

    public CommentDto setContent(String content) {
        this.content = content;
        return this;
    }

    public String getOwnerOfComment() {
        return ownerOfComment;
    }

    public CommentDto setOwnerOfComment(String ownerOfComment) {
        this.ownerOfComment = ownerOfComment;
        return this;
    }

    public String getJokeId() {
        return jokeId;
    }

    public CommentDto setJokeId(String jokeId) {
        this.jokeId = jokeId;
        return this;
    }
}
