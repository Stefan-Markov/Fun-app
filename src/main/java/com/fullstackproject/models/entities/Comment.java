package com.fullstackproject.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    private String content;
    private Joke joke;
    private String ownerOfComment;
    private LocalDateTime localDate = LocalDateTime.now();

    public Comment() {
    }

    @Column(name = "date_created", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public Comment setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
        return this;
    }

    @Column(name = "owner_of_comment")
    public String getOwnerOfComment() {
        return ownerOfComment;
    }

    public Comment setOwnerOfComment(String ownerOfComment) {
        this.ownerOfComment = ownerOfComment;
        return this;
    }

    @Column(name = "content", columnDefinition = "TEXT")
    @Length(min = 2, message = "Enter at least 2 symbols for content!")
    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    @ManyToOne
    @JsonIgnore
    public Joke getJoke() {
        return joke;
    }

    public Comment setJoke(Joke joke) {
        this.joke = joke;
        return this;
    }
}
