package com.fullstackproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jokes")
public class Joke extends BaseEntity {

    private String title;
    private String content;
    private User user;
    private String keyword;
    private List<Comment> comments;
    private String creator;
    private List<Likes> likes;
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "joke")
    public List<Likes> getLikes() {
        return likes;
    }

    public Joke setLikes(List<Likes> likes) {
        this.likes = likes;
        return this;
    }

    @OneToMany(mappedBy = "joke")
    public List<Comment> getComments() {
        return comments;
    }

    public Joke setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", keyword='" + keyword + '\'' +
                ", creator='" + creator + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public Joke setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    @Column(name = "keyword")
    @Length(min = 3,max = 100, message = "Keyword must be at least 3 and maximum 100 symbols.")
    public String getKeyword() {
        return keyword;
    }

    public Joke setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Column(name = "created_info_user", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Joke setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Column(name = "title")
    @NotNull(message = "Enter valid title!")
    @Length(min = 3,max = 100, message = "Enter at least 3 and maximum 100 symbols for title!")
    public String getTitle() {
        return title;
    }

    public Joke setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    @NotNull(message = "Enter valid content!")
    @Length(min = 3,message = "Enter at least 3 symbols for content!")
    public String getContent() {
        return content;
    }

    public Joke setContent(String content) {
        this.content = content;
        return this;
    }

    @ManyToOne
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public Joke setUser(User user) {
        this.user = user;
        return this;
    }

}
