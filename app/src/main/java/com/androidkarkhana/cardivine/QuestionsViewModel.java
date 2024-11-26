package com.androidkarkhana.cardivine;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.androidkarkhana.cardivine.data.*;
import java.util.List;
import java.util.ArrayList;

public class QuestionsViewModel extends AndroidViewModel {
    private QuestionRepository repository;
    private String currentCategoryId;
    private LiveData<List<QuestionEntity>> questions;
    private MutableLiveData<CategoryProgress> categoryProgress = new MutableLiveData<>();
    private boolean hasGeneratedNewQuestions = false;
    private Handler mainHandler;
    private int currentSet = 1; // Track current question set (1: initial, 2: refill)

    public QuestionsViewModel(Application application) {
        super(application);
        repository = new QuestionRepository(application);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void init(Application application, String categoryId) {
        this.currentCategoryId = categoryId;
        this.questions = repository.getQuestionsForCategory(categoryId);
        
        repository.getUnviewedQuestionCount(categoryId, count -> {
            if (count == 0) {
                repository.resetCategoryProgress(categoryId);
                initializeQuestionsForCategory();
            }
        });
    }

    public LiveData<List<QuestionEntity>> getQuestions() {
        return questions;
    }

    public void updateProgress(int position) {
        repository.updateCategoryProgress(currentCategoryId, position);
        repository.markQuestionViewed(position);
    }

    public void checkAndHandleQuestionRefill(int currentPosition, int totalQuestions) {
        if (currentPosition >= totalQuestions - 1) {
            repository.getUnviewedQuestionCount(currentCategoryId, count -> {
                if (count <= 1) {
                    if (currentSet == 1 && !hasGeneratedNewQuestions) {
                        refillQuestions();
                        hasGeneratedNewQuestions = true;
                        currentSet = 2;
                        showToast("New questions are now available!");
                    } else if (currentSet == 2) {
                        showToast("Congratulations! You've completed all questions in this category!");
                    }
                }
            });
        }
    }

    private void showToast(String message) {
        mainHandler.post(() -> 
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        );
    }

    public void loadCategoryProgress(LoadProgressCallback callback) {
        repository.getCategoryProgress(currentCategoryId, progress -> {
            categoryProgress.postValue(progress);
            if (callback != null) {
                callback.onProgressLoaded(progress);
            }
        });
    }

    private void refillQuestions() {
        List<QuestionEntity> newQuestions = generateNewQuestions();
        repository.insertQuestions(newQuestions);
    }

    private void initializeQuestionsForCategory() {
        List<QuestionEntity> initialQuestions = getInitialQuestions();
        repository.insertQuestions(initialQuestions);
    }

    private List<QuestionEntity> generateNewQuestions() {
        List<QuestionEntity> newQuestions = new ArrayList<>();
        switch (currentCategoryId) {
            case "deep":
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your biggest regret in life?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What would you do if you had one year left to live?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's the hardest truth you've had to accept?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your biggest fear about the future?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's the most valuable life lesson you've learned?"));
                break;
            case "friends":
                newQuestions.add(new QuestionEntity(currentCategoryId, "What do you value most in our friendship?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's one thing you've always wanted to tell me?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your favorite memory with me?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's something you wish we did more often?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's the craziest adventure we should plan?"));
                break;
            case "meeting":
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your passion in life?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your biggest dream?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What makes you unique?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your idea of a perfect day?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your biggest accomplishment?"));
                break;
            case "siblings":
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your favorite childhood memory with me?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What do you wish we did more together?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's one thing you've learned from me?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your favorite family tradition?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's something you want to change about our relationship?"));
                break;
            default:
                newQuestions.add(new QuestionEntity(currentCategoryId, "What makes you happy?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your biggest dream?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your favorite memory?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's something unique about you?"));
                newQuestions.add(new QuestionEntity(currentCategoryId, "What's your life goal?"));
                break;
        }
        return newQuestions;
    }

    private List<QuestionEntity> getInitialQuestions() {
        List<QuestionEntity> questions = new ArrayList<>();
        switch (currentCategoryId) {
            case "deep":
                questions.add(new QuestionEntity(currentCategoryId, "What's your biggest fear in life?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's the most important lesson you've learned?"));
                questions.add(new QuestionEntity(currentCategoryId, "What does success mean to you?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your purpose in life?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your definition of happiness?"));
                break;
            case "friends":
                questions.add(new QuestionEntity(currentCategoryId, "When did you realize we were best friends?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's the craziest thing we've done together?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's one thing you'd change about me?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your favorite thing about our friendship?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's one secret you've never told me?"));
                break;
            case "meeting":
                questions.add(new QuestionEntity(currentCategoryId, "What's your life goal?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your ideal weekend?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your favorite way to spend time?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your biggest achievement?"));
                questions.add(new QuestionEntity(currentCategoryId, "What makes you laugh the most?"));
                break;
            case "siblings":
                questions.add(new QuestionEntity(currentCategoryId, "What's your earliest memory of us?"));
                questions.add(new QuestionEntity(currentCategoryId, "What do you think our parents did right?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's the most annoying thing I do?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your favorite family tradition?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's one thing you wish we did as kids?"));
                break;
            default:
                questions.add(new QuestionEntity(currentCategoryId, "What's your favorite memory?"));
                questions.add(new QuestionEntity(currentCategoryId, "What makes you unique?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your biggest achievement?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your dream vacation?"));
                questions.add(new QuestionEntity(currentCategoryId, "What's your biggest goal in life?"));
                break;
        }
        return questions;
    }

    public interface LoadProgressCallback {
        void onProgressLoaded(CategoryProgress progress);
    }
}