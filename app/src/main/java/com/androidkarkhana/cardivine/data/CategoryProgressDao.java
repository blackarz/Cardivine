package com.androidkarkhana.cardivine.data;

import androidx.room.*;

@Dao
public interface CategoryProgressDao {
    @Query("SELECT * FROM category_progress WHERE categoryId = :categoryId")
    CategoryProgress getCategoryProgress(String categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(CategoryProgress progress);

    @Query("UPDATE category_progress SET lastViewedQuestionPosition = :position, lastAccessTimestamp = :timestamp WHERE categoryId = :categoryId")
    void updateProgress(String categoryId, int position, long timestamp);

    @Query("UPDATE category_progress SET lastViewedQuestionPosition = 0, lastAccessTimestamp = 0 WHERE categoryId = :categoryId")
    void resetProgress(String categoryId);
}