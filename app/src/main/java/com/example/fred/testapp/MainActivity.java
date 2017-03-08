package com.example.fred.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fred.testapp.questions.QuestionsActivity;

public class MainActivity extends AppCompatActivity {

    SeekBar mySeekBar;
    TextView myTextView;
    public String parameterDifficulty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = (TextView) findViewById(R.id.numberView);
        mySeekBar = (SeekBar) findViewById(R.id.numberAction);
        mySeekBar.setMax(20);

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String currentValue = String.valueOf(progress);
                myTextView.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /*public void sendMessage(View view) {
        final TextView myTextView = (TextView) findViewById(R.id.numberView);
        myTextView.setText("hey");


        Intent intent = new Intent(this, QuestionsActivity.class);
        String message = "hey";
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

         parameterDifficulty = "";

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.easy_button:
                if (checked)
                    parameterDifficulty = "&difficulty=easy";
                    break;
            case R.id.medium_button:
                if (checked)
                    parameterDifficulty = "&difficulty=medium";
                    break;
            case R.id.hard_button:
                if (checked)
                    parameterDifficulty = "&difficulty=hard";
                break;
            default:
                parameterDifficulty = "";
                break;
        }
    }

    public void sendMessage(View view) {
        Log.d("changeView", String.valueOf(view));
        Intent intent = new Intent(this, QuestionsActivity.class);

        this.myTextView.setText("GO !");

        int num_of_questions = this.mySeekBar.getProgress();
        intent.putExtra("NUM_OF_QUESTIONS", num_of_questions);
        intent.putExtra("PARAMETER_DIFFICULTY", parameterDifficulty);
        startActivity(intent);
    }
}
