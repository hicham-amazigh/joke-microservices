package com.joke.common.dto;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Introspected
public class CreateJokeRequest {
    @NotBlank
    @Size(min = 10, max = 2000)
    private String setup;

    @NotBlank
    @Size(min = 5, max = 1000)
    private String punchline;

    @Size(max = 50)
    private String category;

    private Long userId;

    public CreateJokeRequest() {}

    public CreateJokeRequest(String setup, String punchline) {
        this.setup = setup;
        this.punchline = punchline;
    }

    public String getSetup() { return setup; }
    public void setSetup(String setup) { this.setup = setup; }

    public String getPunchline() { return punchline; }
    public void setPunchline(String punchline) { this.punchline = punchline; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
