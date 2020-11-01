package com.example.quiztimeapp.feature_makequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quiztimeapp.R;
import com.example.quiztimeapp.objectClasses.question;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        ArrayList<question> ques_list = MakeQuizActivity.my_questions;

    }
}