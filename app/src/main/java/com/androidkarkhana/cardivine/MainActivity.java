package com.androidkarkhana.cardivine;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkarkhana.cardivine.data.QuestionDatabase;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> allCategories;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Select a Pack");

        QuestionDatabase.getDatabase(this);

        recyclerView = findViewById(R.id.categoriesRecyclerView);
        tabLayout = findViewById(R.id.tabLayout);
        
        setupCategories();
        setupRecyclerView();
        setupTabs();
    }

    private void setupCategories() {
        allCategories = new ArrayList<>();
        allCategories.add(new Category("deep", "Deep Questions", "Questions that hit deep.", "✨", "deep"));
        allCategories.add(new Category("latenight", "Late Night Talks", "Get to know each other — for real.", "🌙", "deep", "couple"));
        allCategories.add(new Category("friends", "For Best Friends", "How well do you really know them?", "✌️", "gossip"));
        allCategories.add(new Category("meeting", "Getting to Know", "Questions to meet someone new.", "👥", "deep", "couple"));
        allCategories.add(new Category("siblings", "For Siblings", "Ask each other before it's too late.", "🖤", "deep", "gossip"));
        allCategories.add(new Category("party", "Party Time", "Fun and entertaining questions.", "🎉", "gossip"));
        allCategories.add(new Category("life", "Life's Mysteries", "Philosophical and thought-provoking.", "🌟", "deep"));
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(filterCategories("deep"), category -> {
            QuestionsActivity.start(this, category);
        });
        recyclerView.setAdapter(categoryAdapter);
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Deep"));
        tabLayout.addTab(tabLayout.newTab().setText("Couple"));
        tabLayout.addTab(tabLayout.newTab().setText("Gossip"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String filter = tab.getText().toString().toLowerCase();
                categoryAdapter.updateCategories(filterCategories(filter));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private List<Category> filterCategories(String tag) {
        return allCategories.stream()
                .filter(category -> category.getTags().contains(tag))
                .collect(Collectors.toList());
    }
}