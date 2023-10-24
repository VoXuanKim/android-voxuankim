package com.example.lab6;

public class Word {
    private String id;
    private String text;

    public Word() {

    }

    public Word(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}

