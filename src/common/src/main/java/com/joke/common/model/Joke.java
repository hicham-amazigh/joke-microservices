package com.joke.common.model;

public class Joke {
    private String text;
    public Joke() {}
    public Joke(String text) { this.text = text; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
