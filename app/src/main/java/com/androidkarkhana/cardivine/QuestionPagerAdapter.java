package com.androidkarkhana.cardivine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.androidkarkhana.cardivine.data.QuestionEntity;
import java.util.ArrayList;
import java.util.List;

public class QuestionPagerAdapter extends RecyclerView.Adapter<QuestionPagerAdapter.QuestionViewHolder> {
    private List<QuestionEntity> questions;

    public QuestionPagerAdapter() {
        this.questions = new ArrayList<>();
    }

    public void submitList(List<QuestionEntity> newQuestions) {
        this.questions = new ArrayList<>(newQuestions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionEntity question = questions.get(position);
        holder.questionText.setText(question.questionText);
        
        // Add position indicator
        String positionText = String.format("%d/%d", position + 1, getItemCount());
        holder.positionText.setText(positionText);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        TextView positionText;
        TextView brandingText;

        QuestionViewHolder(View view) {
            super(view);
            questionText = view.findViewById(R.id.questionText);
            positionText = view.findViewById(R.id.positionText);
            brandingText = view.findViewById(R.id.brandingText);
        }
    }
}