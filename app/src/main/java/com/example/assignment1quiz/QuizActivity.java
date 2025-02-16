package com.example.assignment1quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment1quiz.model.Question;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewQuestionNumber;
    private RadioGroup radioGroupOptions;
    private RadioButton radioButtonOption1, radioButtonOption2, radioButtonOption3, radioButtonOption4;
    private Button buttonPrevious;

    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    //  -1 means not answered yet
    private int[] userAnswers;

    //will get userName from Intent
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        userName = getIntent().getStringExtra("USER_NAME");



        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);

        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioButtonOption1 = findViewById(R.id.radioButtonOption1);
        radioButtonOption2 = findViewById(R.id.radioButtonOption2);
        radioButtonOption3 = findViewById(R.id.radioButtonOption3);
        radioButtonOption4 = findViewById(R.id.radioButtonOption4);

        buttonPrevious = findViewById(R.id.buttonPrevious);
        Button buttonNext = findViewById(R.id.buttonNext);




        questionList = new ArrayList<>();
        loadQuestions();


        userAnswers = new int[questionList.size()];
        Arrays.fill(userAnswers, -1);


        displayQuestion(currentQuestionIndex);


        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUserAnswer();

                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion(currentQuestionIndex);
                }
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUserAnswer();

                if (currentQuestionIndex < questionList.size() - 1) {

                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {

                    calculateScore();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("SCORE", score);
                    intent.putExtra("TOTAL_QUESTIONS", questionList.size());
                    intent.putExtra("USER_NAME", userName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loadQuestions() {

        questionList.add(new Question(
                "What is the capital of France?",
                new String[]{"Berlin", "Madrid", "Paris", "London"},
                2 // 0-based index -> "Paris" is correct
        ));

        questionList.add(new Question(
                "Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"},
                1
        ));

        questionList.add(new Question(
                "Who developed the theory of relativity?",
                new String[]{"Newton", "Edison", "Einstein", "Tesla"},
                2
        ));

        questionList.add(new Question(
                "Which is the largest ocean on Earth?",
                new String[]{"Atlantic", "Pacific", "Arctic", "Indian"},
                1
        ));

        questionList.add(new Question(
                "Which country is famous for pizza?",
                new String[]{"Italy", "India", "Canada", "China"},
                0
        ));
    }

    private void displayQuestion(int index) {

        textViewQuestionNumber.setText("Question " + (index + 1) + " of " + questionList.size());


        Question currentQuestion = questionList.get(index);


        textViewQuestion.setText(currentQuestion.getQuestionText());

        // Set options text
        radioButtonOption1.setText(currentQuestion.getOptions()[0]);
        radioButtonOption2.setText(currentQuestion.getOptions()[1]);
        radioButtonOption3.setText(currentQuestion.getOptions()[2]);
        radioButtonOption4.setText(currentQuestion.getOptions()[3]);


        radioGroupOptions.clearCheck();


        if(userAnswers[index] != -1) {
            int savedAnswerIndex = userAnswers[index];
            switch (savedAnswerIndex) {
                case 0:
                    radioButtonOption1.setChecked(true);
                    break;
                case 1:
                    radioButtonOption2.setChecked(true);
                    break;
                case 2:
                    radioButtonOption3.setChecked(true);
                    break;
                case 3:
                    radioButtonOption4.setChecked(true);
                    break;
            }
        }


        buttonPrevious.setEnabled(index != 0);
    }

    private void saveUserAnswer() {

        int selectedId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {

            userAnswers[currentQuestionIndex] = -1;
        } else {

            int answerIndex = -1;
            if (selectedId == R.id.radioButtonOption1) {
                answerIndex = 0;
            } else if (selectedId == R.id.radioButtonOption2) {
                answerIndex = 1;
            } else if (selectedId == R.id.radioButtonOption3) {
                answerIndex = 2;
            } else if (selectedId == R.id.radioButtonOption4) {
                answerIndex = 3;
            }
            userAnswers[currentQuestionIndex] = answerIndex;
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (userAnswers[i] == questionList.get(i).getCorrectAnswerIndex()) {
                score++;
            }
        }
    }
}
