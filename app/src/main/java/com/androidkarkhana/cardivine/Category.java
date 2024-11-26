package com.androidkarkhana.cardivine;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Category implements Serializable {
    private String id;
    private String title;
    private String description;
    private String emoji;
    private List<String> tags;

    public Category(String id, String title, String description, String emoji, String... tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.emoji = emoji;
        this.tags = Arrays.asList(tags);
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getEmoji() { return emoji; }
    public List<String> getTags() { return tags; }
}