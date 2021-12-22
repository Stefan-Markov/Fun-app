package com.fullstackproject.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CommentBinding {

    private String content;
    private String ownerOfComment;
    private String jokeId;

    public CommentBinding() {
    }

    @NotNull(message = "Provide valid username")
    @Length(min = 2, max = 100, message = "provide valid username")
    public String getOwnerOfComment() {
        return ownerOfComment;
    }

    public CommentBinding setOwnerOfComment(String ownerOfComment) {
        this.ownerOfComment = ownerOfComment;
        return this;
    }

    @NotNull(message = "Provide joke id")
    @Length(min = 2, max = 100, message = "provide valid id of joke")
    public String getJokeId() {
        return jokeId;
    }

    public CommentBinding setJokeId(String jokeId) {
        this.jokeId = jokeId;
        return this;
    }

    @NotNull(message = "Please provide at least 2 symbols to comment")
    @Length(min = 2, max = 2000, message = "Enter at least 2 symbols for content!")
    public String getContent() {
        return content;
    }

    public CommentBinding setContent(String content) {
        this.content = content;
        return this;
    }
}
