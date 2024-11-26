package com.androidkarkhana.cardivine.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId ORDER BY id ASC")
    LiveData<List<QuestionEntity>> getQuestionsForCategory(String categoryId);

    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId AND isViewed = 0")
    int getUnviewedQuestionCount(String categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<QuestionEntity> questions);

    @Update
    void update(QuestionEntity question);

    @Query("UPDATE questions SET isViewed = 1, lastViewedTimestamp = :timestamp WHERE id = :questionId")
    void markQuestionAsViewed(int questionId, long timestamp);

    @Query("UPDATE questions SET isViewed = 0, lastViewedTimestamp = 0 WHERE categoryId = :categoryId")
    void resetViewedStatus(String categoryId);

    @Query("DELETE FROM questions WHERE categoryId = :categoryId")
    void deleteAllQuestionsInCategory(String categoryId);
}