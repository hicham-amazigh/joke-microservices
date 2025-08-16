package com.joke.common.model;

public class Quote {
    private String text;
    public Quote() {}
    public Quote(String text) { this.text = text; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}