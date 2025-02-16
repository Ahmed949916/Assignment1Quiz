package com.example.assignment1quiz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private int score, totalQuestions;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewResult = findViewById(R.id.textViewResult);
        TextView textViewUserName = findViewById(R.id.textViewResultUserName);
        Button buttonShare = findViewById(R.id.buttonShare);

        // Get data from Intent
        score = getIntent().getIntExtra("SCORE", 0);
        totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);
        userName = getIntent().getStringExtra("USER_NAME");

        textViewUserName.setText(userName);
        textViewResult.setText(score + "/" + totalQuestions);


        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareMessage = userName + " scored " + score + "/" + totalQuestions + " in the MCQ Quiz!";
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });


    }
}
