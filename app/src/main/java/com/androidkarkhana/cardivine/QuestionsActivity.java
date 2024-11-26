package com.androidkarkhana.cardivine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.androidkarkhana.cardivine.data.QuestionEntity;
import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
    private ViewPager2 questionsPager;
    private QuestionPagerAdapter pagerAdapter;
    private QuestionsViewModel viewModel;
    private String categoryId;

    public static void start(Context context, Category category) {
        Intent intent = new Intent(context, QuestionsActivity.class);
        intent.putExtra("category_id", category.getId());
        intent.putExtra("category_title", category.getTitle());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        categoryId = getIntent().getStringExtra("category_id");
        String categoryTitle = getIntent().getStringExtra("category_title");

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(categoryTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewModel();
        setupViewPager();
        observeQuestions();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(QuestionsViewModel.class);
        viewModel.init(getApplication(), categoryId);
    }

    private void setupViewPager() {
        questionsPager = findViewById(R.id.questionsPager);
        pagerAdapter = new QuestionPagerAdapter();
        questionsPager.setAdapter(pagerAdapter);
        
        questionsPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        questionsPager.setPageTransformer(createPageTransformer());
        
        questionsPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewModel.updateProgress(position);
                viewModel.checkAndHandleQuestionRefill(position, pagerAdapter.getItemCount());
            }
        });
    }

    private void observeQuestions() {
        viewModel.getQuestions().observe(this, questions -> {
            if (questions != null && !questions.isEmpty()) {
                pagerAdapter.submitList(questions);
                restoreLastPosition();
            }
        });
    }

    private void restoreLastPosition() {
        viewModel.loadCategoryProgress(progress -> {
            if (progress != null && progress.lastViewedQuestionPosition > 0) {
                questionsPager.setCurrentItem(progress.lastViewedQuestionPosition, false);
            }
        });
    }

    private ViewPager2.PageTransformer createPageTransformer() {
        return (page, position) -> {
            float MIN_SCALE = 0.85f;
            float MIN_ALPHA = 0.5f;

            if (position < -1 || position > 1) {
                page.setAlpha(0f);
            } else {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}