package com.androidkarkhana.cardivine;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupCategoryCards();
    }

    private void setupCategoryCards() {
        LinearLayout container = findViewById(R.id.categoriesContainer);
        List<Category> categories = getCategoryData();

        for (Category category : categories) {
            CardView card = (CardView) LayoutInflater.from(this)
                    .inflate(R.layout.category_card, container, false);

            TextView titleView = card.findViewById(R.id.categoryTitle);
            TextView descriptionView = card.findViewById(R.id.categoryDescription);

            titleView.setText(category.title);
            descriptionView.setText(category.description);

            card.setOnClickListener(v -> openQuestionsActivity(category));

            container.addView(card);
        }
    }

    private void openQuestionsActivity(Category category) {
        // Start QuestionActivity with category data
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("CATEGORY_ID", category.id);
        startActivity(intent);
    }

    private List<Category> getCategoryData() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Reflection Questions", "Explore your thoughts and feelings", getSelfReflectionQuestions()));
        categories.add(new Category(2, "Friendship Topics", "Strengthen your bonds", getFriendshipQuestions()));
        categories.add(new Category(3, "Life Goals", "Plan your future together", getLifeGoalsQuestions()));
        categories.add(new Category(4, "Memory Lane", "Share your stories", getMemoryQuestions()));
        categories.add(new Category(5, "Values & Beliefs", "Understand each other better", getValuesQuestions()));
        return categories;
    }

    private List<String> getSelfReflectionQuestions() {
        List<String> questions = new ArrayList<>();
        questions.add("What achievement are you most proud of?");
        questions.add("What's your biggest dream for the future?");
        questions.add("What habit would you like to change?");
        questions.add("What's the best advice you've ever received?");
        questions.add("What makes you feel truly alive?");
        return questions;
    }


    private List<String> getFriendshipQuestions() {
        List<String> questions = new ArrayList<>();
        questions.add("What achievement are you most proud of?");
        questions.add("What's your biggest dream for the future?");
        questions.add("What habit would you like to change?");
        questions.add("What's the best advice you've ever received?");
        questions.add("What makes you feel truly alive?");
        return questions;
    }


    private List<String> getLifeGoalsQuestions() {
        List<String> questions = new ArrayList<>();
        questions.add("What achievement are you most proud of?");
        questions.add("What's your biggest dream for the future?");
        questions.add("What habit would you like to change?");
        questions.add("What's the best advice you've ever received?");
        questions.add("What makes you feel truly alive?");
        return questions;
    }

    private List<String> getMemoryQuestions() {
        List<String> questions = new ArrayList<>();
        questions.add("What achievement are you most proud of?");
        questions.add("What's your biggest dream for the future?");
        questions.add("What habit would you like to change?");
        questions.add("What's the best advice you've ever received?");
        questions.add("What makes you feel truly alive?");
        return questions;
    }


    private List<String> getValuesQuestions() {
        List<String> questions = new ArrayList<>();
        questions.add("What achievement are you most proud of?");
        questions.add("What's your biggest dream for the future?");
        questions.add("What habit would you like to change?");
        questions.add("What's the best advice you've ever received?");
        questions.add("What makes you feel truly alive?");
        return questions;
    }
    // Additional question lists for other categories...
}