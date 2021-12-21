package com.fullstackproject.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserBinding {

    private String username;
    private String email;
    private String password;


    public UserBinding() {
    }


    @Email(message = "Enter valid email!")
    @Length(min = 3, max = 100, message = "Provide email between 3 and 100 symbols.")
    public String getEmail() {
        return email;
    }

    public UserBinding setEmail(String email) {
        this.email = email;
        return this;
    }


    @Length(min = 5, max = 100, message = "Password must be between five and one hundred symbols")
    @NotNull(message = "Password has to be filled!")
    public String getPassword() {
        return password;
    }

    public UserBinding setPassword(String password) {
        this.password = password;
        return this;
    }

    @Length(min = 5, max = 100, message = "Username must be between five and one hundred symbols")
    @NotNull(message = "Username has to be filled!")
    public String getUsername() {
        return username;
    }

    public UserBinding setUsername(String username) {
        this.username = username;
        return this;
    }
}
