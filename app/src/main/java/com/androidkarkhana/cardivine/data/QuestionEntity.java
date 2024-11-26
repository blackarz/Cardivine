package com.androidkarkhana.cardivine.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class QuestionEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String categoryId;
    public String questionText;
    public boolean isViewed;
    public long lastViewedTimestamp;

    public QuestionEntity(String categoryId, String questionText) {
        this.categoryId = categoryId;
        this.questionText = questionText;
        this.isViewed = false;
        this.lastViewedTimestamp = 0;
    }
}