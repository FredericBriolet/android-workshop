package com.example.fred.testapp.questions;

/**
 * Created by Fred on 07/03/2017.
 */

public class Question {
    public int difficulty;
    public String text;
    public String category;
    public String answer;

    public Question(String text, int difficulty, String category, String answer) {
        this.text = text;
        this.difficulty = difficulty;
        this.category = category;
        this.answer= answer;
    }
}
