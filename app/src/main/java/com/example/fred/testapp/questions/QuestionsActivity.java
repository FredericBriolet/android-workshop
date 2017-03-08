package com.example.fred.testapp.questions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fred.testapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<Question> questions = new ArrayList<Question>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //Get Data
        int numberOfquestion = extras.getInt("NUM_OF_QUESTIONS");
        String difficultyParameter = extras.getString("PARAMETER_DIFFICULTY");
        Log.d("num_of_questions", String.valueOf(numberOfquestion));
        this.fetchQuestions(numberOfquestion, difficultyParameter);

        //Base UI
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) this.findViewById(R.id.question_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new QuestionAdapter(questions));
    }

    void displayQuestion(ArrayList<Question> questions) {
        recyclerView.setAdapter(new QuestionAdapter(questions));
    }


    protected ArrayList<Question> parseResultsRequestQuestion(JSONArray results) {

        ArrayList<Question> questions = new ArrayList<Question>();

        for (int i = 0; i < results.length(); i++) {
            String title = null;
            try {
                title = (String) results.getJSONObject(i).get("question");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String difficulty = null;
            try {
                difficulty = (String) results.getJSONObject(i).get("difficulty");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String category = null;
            try {
                category = (String) results.getJSONObject(i).get("category");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String answer = null;
            try {
                answer = (String) results.getJSONObject(i).get("correct_answer");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(title != null && difficulty != null && category != null && answer != null) {
                Question question = new Question(convertCharacter(title), difficultyToInt(difficulty), convertCharacter(category), convertCharacter(answer));
                questions.add(question);
            }
        }

        return questions;
    }


    protected String convertCharacter(String string) {
        String result;
        result = string.replace("&quot;", "\"");
        result = result.replace("&eacute;", "é");
        result = result.replace("&uuml;", "ü");
        return result.replace("&#039;", "'");
    }

    protected int difficultyToInt(String difficulty) {
        switch (difficulty){
            case "easy":
                return 0;
            case "medium":
                return 1;
            case "hard":
                return 2;
            default:
                return 0;
        }
    }

    void fetchQuestions(final int numberOfQuestions, final String difficultyParameter){

        final String url = "https://opentdb.com/api.php?amount=" + numberOfQuestions + difficultyParameter;
        final RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("response json", String.valueOf(response));
                try {

                    JSONArray results = response.getJSONArray("results");

                    Log.d("response json results", String.valueOf(results));

                    questions = parseResultsRequestQuestion(results);

                    //Update ui
                    recyclerView.setAdapter(new QuestionAdapter(questions));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("response json error", String.valueOf(e));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonRequest);
    }
}
