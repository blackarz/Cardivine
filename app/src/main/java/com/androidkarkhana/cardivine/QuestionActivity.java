package com.androidkarkhana.cardivine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private CardView questionCard;
    private TextView questionText;
    private TextView questionCounter;
    private List<String> questions;
    private int currentQuestionIndex = 0;
    private Button nextButton;
    private Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Initialize views
        questionCard = findViewById(R.id.questionCard);
        questionText = findViewById(R.id.questionText);
        questionCounter = findViewById(R.id.questionCounter);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);

        // Get category ID from intent
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        // Get questions for this category
        Category category = getCategoryById(categoryId);
        if (category != null) {
            questions = category.questions;
            setTitle(category.title);
            updateQuestion();
        }

        setupButtons();
    }

    private void setupButtons() {
        nextButton.setOnClickListener(v -> showNextQuestion());
        previousButton.setOnClickListener(v -> showPreviousQuestion());

        // Initially disable previous button
        updateButtonStates();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            Animation slideOutLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
            Animation slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

            slideOutLeft.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    currentQuestionIndex++;
                    updateQuestion();
                    questionCard.startAnimation(slideInRight);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            questionCard.startAnimation(slideOutLeft);
        }
    }

    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            Animation slideOutRight = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
            Animation slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);

            slideOutRight.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    currentQuestionIndex--;
                    updateQuestion();
                    questionCard.startAnimation(slideInLeft);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            questionCard.startAnimation(slideOutRight);
        }
    }

    private void updateQuestion() {
        questionText.setText(questions.get(currentQuestionIndex));
        questionCounter.setText(String.format("%d/%d", currentQuestionIndex + 1, questions.size()));
        updateButtonStates();
    }

    private void updateButtonStates() {
        previousButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);

        previousButton.setAlpha(previousButton.isEnabled() ? 1.0f : 0.5f);
        nextButton.setAlpha(nextButton.isEnabled() ? 1.0f : 0.5f);
    }

    private Category getCategoryById(int categoryId) {
        // This should be replaced with proper data management
        // For now, we'll create a sample category
        List<String> questions = new ArrayList<>();
        questions.add("What's your biggest dream in life?");
        questions.add("What's a childhood memory that always makes you smile?");
        questions.add("If you could master any skill instantly, what would it be?");
        questions.add("What's the most valuable life lesson you've learned?");
        questions.add("Where do you see yourself in 5 years?");
        questions.add("What's your idea of perfect happiness?");
        questions.add("What's the best advice you would give to your younger self?");
        questions.add("What's one thing you want to accomplish this year?");

        return new Category(categoryId, "Deep Questions", "Questions that make you think", questions);
    }
}