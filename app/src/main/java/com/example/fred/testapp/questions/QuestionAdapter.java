package com.example.fred.testapp.questions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fred.testapp.R;

import java.util.ArrayList;

/**
 * Created by Fred on 07/03/2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<Question> questions;

    public QuestionAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context ctx = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(ctx);
        final View questionItemView = inflater.inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(questionItemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        final Question question = questions.get(position);
        holder.questionText.setText(question.text);
        holder.questionCategory.setText(question.category);

        final Context ctx = holder.itemView.getContext();

        // Color
        String color = "#FF00FF";
        switch (question.difficulty){
            case 0:
                color = "#4CAF50";
                break;
            case 1:
                color = "#FFC107";
                break;
            case 2:
                color = "#F44336";
                break;
            default:
                color = "#000000";
                break;
        }
        holder.questionText.setTextColor(Color.parseColor(color));

        // Category
        holder.questionCategory.setTypeface(null, Typeface.ITALIC);

        holder.questionText.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                showAnswer(ctx, question.answer);
            }
        });

    }

    public void showAnswer(Context ctx, String answer) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(ctx);
        dlgAlert.setMessage(answer);
        dlgAlert.setTitle("Answer");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionCategory;
        TextView questionText;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            this.questionText = (TextView) itemView.findViewById(R.id.question_text);
            this.questionCategory = (TextView) itemView.findViewById(R.id.question_category);
        }
    }

}
