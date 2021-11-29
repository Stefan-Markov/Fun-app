package com.fullstackproject.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class JokeEditDTO {

    private String title;
    private String content;
    private String id;
    private String keyword;

    public JokeEditDTO() {
    }
    @NotNull(message = "Enter valid title!")
    @Length(min = 3, message = "Enter et least 3 symbols for title!")
    public String getTitle() {
        return title;
    }

    public JokeEditDTO setTitle(String title) {
        this.title = title;
        return this;
    }
    @NotNull(message = "Enter valid content!")
    @Length(min = 3, message = "Enter et least 3 symbols for content!")
    public String getContent() {
        return content;
    }

    public JokeEditDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getId() {
        return id;
    }

    public JokeEditDTO setId(String id) {
        this.id = id;
        return this;
    }
    @Length(min = 3, message = "Keyword must be at least 3 symbols.")
    @NotNull(message = "Enter valid keyword!")
    public String getKeyword() {
        return keyword;
    }

    public JokeEditDTO setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public String toString() {
        return "JokeEditDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
