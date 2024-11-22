package com.androidkarkhana.cardivine;

import java.util.List;

public class Category {
    public int id;
    public String title;
    public String description;
    public List<String> questions;

    public Category(int id, String title, String description, List<String> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }
}
