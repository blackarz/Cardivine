package com.androidkarkhana.cardivine.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "category_progress")
public class CategoryProgress {
    @PrimaryKey
    @NonNull
    public String categoryId;
    
    public int lastViewedQuestionPosition;
    public long lastAccessTimestamp;

    public CategoryProgress(@NonNull String categoryId) {
        this.categoryId = categoryId;
        this.lastViewedQuestionPosition = 0;
        this.lastAccessTimestamp = System.currentTimeMillis();
    }
}