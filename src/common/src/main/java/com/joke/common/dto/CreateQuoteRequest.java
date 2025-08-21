package com.joke.common.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Serdeable
@Introspected
public class CreateQuoteRequest {
    @NotBlank
    @Size(min = 10, max = 1000)
    private String text;

    @Size(max = 100)
    private String author;

    @Size(max = 50)
    private String category;

    private String userId;

    public CreateQuoteRequest() {}

    public CreateQuoteRequest(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
