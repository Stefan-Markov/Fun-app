package com.fullstackproject.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class JokeBinding {
    private String title;
    private String content;
    private String username;
    private String keyword;

    @Length(min = 3, max = 100, message = "Keyword must be at least 3 and maximum 100 symbols.")
    @NotNull(message = "Enter valid keyword!")
    public String getKeyword() {
        return keyword;
    }

    public JokeBinding setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @NotNull(message = "Enter valid title!")
    @Length(min = 3, max = 100, message = "Enter at least 3 and maximum 100 symbols for title!")
    public String getTitle() {
        return title;
    }

    public JokeBinding setTitle(String title) {
        this.title = title;
        return this;
    }

    @NotNull(message = "Enter valid content!")
    @Length(min = 3, max = 2000, message = "Enter at least 3 symbols for content!")
    public String getContent() {
        return content;
    }

    public JokeBinding setContent(String content) {
        this.content = content;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public JokeBinding setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "JokeBinding{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}