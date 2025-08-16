package com.joke.common.dto;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Introspected
public class CreateUserRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 100)
    private String displayName;

    public CreateUserRequest() {}

    public CreateUserRequest(String username, String email, String displayName) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
