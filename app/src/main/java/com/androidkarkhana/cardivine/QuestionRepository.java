package com.androidkarkhana.cardivine;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.androidkarkhana.cardivine.data.*;

public class QuestionRepository {
    private QuestionDao questionDao;
    private CategoryProgressDao categoryProgressDao;
    private final ExecutorService executorService;

    public QuestionRepository(Application application) {
        QuestionDatabase db = QuestionDatabase.getDatabase(application);
        questionDao = db.questionDao();
        categoryProgressDao = db.categoryProgressDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<QuestionEntity>> getQuestionsForCategory(String categoryId) {
        return questionDao.getQuestionsForCategory(categoryId);
    }

    public void insertQuestions(List<QuestionEntity> questions) {
        executorService.execute(() -> questionDao.insertAll(questions));
    }

    public void markQuestionViewed(int questionId) {
        executorService.execute(() -> 
            questionDao.markQuestionAsViewed(questionId, System.currentTimeMillis())
        );
    }

    public void updateCategoryProgress(String categoryId, int position) {
        executorService.execute(() -> 
            categoryProgressDao.updateProgress(categoryId, position, System.currentTimeMillis())
        );
    }

    public void getCategoryProgress(String categoryId, ProgressCallback callback) {
        executorService.execute(() -> {
            CategoryProgress progress = categoryProgressDao.getCategoryProgress(categoryId);
            callback.onProgressLoaded(progress);
        });
    }

    public void getUnviewedQuestionCount(String categoryId, CountCallback callback) {
        executorService.execute(() -> {
            int count = questionDao.getUnviewedQuestionCount(categoryId);
            callback.onCountLoaded(count);
        });
    }

    public void resetCategoryProgress(String categoryId) {
        executorService.execute(() -> {
            questionDao.resetViewedStatus(categoryId);
            categoryProgressDao.resetProgress(categoryId);
        });
    }

    public interface ProgressCallback {
        void onProgressLoaded(CategoryProgress progress);
    }

    public interface CountCallback {
        void onCountLoaded(int count);
    }
}