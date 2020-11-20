package com.example.quiztimeapp.feature_makequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quiztimeapp.R;
import com.example.quiztimeapp.adapters.listViewAdaper_forQuestions;
import com.example.quiztimeapp.adapters.listViewAdapter_forMultipleChoiceAns;
import com.example.quiztimeapp.objectClasses.Answer;
import com.example.quiztimeapp.objectClasses.question;

import java.util.ArrayList;

public class PreviewQuizActivity extends AppCompatActivity {
    TextView question_txtView;
    ListView answer_listView;
    listViewAdapter_forMultipleChoiceAns my_adapter;

    ArrayList<question> question_list;

    private int currentQuestion = 0;
    private int totalQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_quiz);

        //hook view
        question_txtView = findViewById(R.id.textView_preview_question_content);
        answer_listView = findViewById(R.id.listView_preview_answerList);

        //get question_list from intent
        Intent intent = getIntent();
        question_list = (ArrayList<question>) intent.getSerializableExtra("QUESTION_LIST");
        totalQuestion = intent.getIntExtra("QUESTION_TOTAL",0);
        Log.d(PreviewQuizActivity.class.getSimpleName(), "PREVIEW_ACTIVITY - onCreate: total ques: "+totalQuestion + " \nconfirm with question_list_total: "+question_list.size());

        //initially show 1st question content
        if (totalQuestion == question_list.size()) {
            question_txtView.setText(question_list.get(0).getQuestion_content());
            setupListView(decodeAnswers(question_list.get(0).getAnswer()));
        }
        else
        {
            //no question or error
            if (totalQuestion==0 && totalQuestion == question_list.size())
            {
                //no question case
                //pop message => inform no question -> either user go back or exit & save draft
            }
            else
            {
                //error
                Log.d(PreviewQuizActivity.class.getSimpleName(), "PREVIEW_ACTIVITY - onCreate: ERROR as NOT MATCH INFORMATION!");
            }
            finish();

        }
    }
    private ArrayList<Answer> decodeAnswers(String answer)
    {
        Log.d(PreviewQuizActivity.class.getSimpleName(), "DECODE ANSWERS STRING PROCEES...\n Answer String: "+ answer);
        String a = answer;
        ArrayList<Answer> myAns = new ArrayList<Answer>();
        String temp = a.replaceAll("////","");
        Log.d(PreviewQuizActivity.class.getSimpleName(), "DECODE ANSWERS STRING PROCEES...\n a=  "+ a);
        Answer answer1 = new Answer(a,true);
        myAns.add(answer1);

        // delete first //////// (8) in string
       /* a = a.replace("////////","");
        while (a.isEmpty()==false)
        {
            if (a[0]== "[")
            {
                // make sure 1st char is [ -> for [type ~ #answer]

            }
        }*/
        return myAns;


    }
    private void setupListView( ArrayList<Answer> my_answers) {

        Log.d(AddQuestionActivity.class.getSimpleName(),"SETUP LIST VIEW: Setting up list view...");

        Log.d(AddQuestionActivity.class.getSimpleName(),"list items: "+my_answers.size());

        my_adapter = new listViewAdapter_forMultipleChoiceAns(this,R.layout.list_multiple_choice_item,my_answers);
        answer_listView.setAdapter(my_adapter);
    }

    public void handle_finishPreview_clicked(View view) {

    }

    public void handle_Next_clicked(View view) {
        //update  current question
        currentQuestion+= 1;
        if (currentQuestion>totalQuestion)
        {
            //back to beginning
            currentQuestion=0;
        }
        question_txtView.setText(question_list.get(currentQuestion).getQuestion_content());
        setupListView(decodeAnswers(question_list.get(currentQuestion).getAnswer()));

    }

    public void handle_Back_clicked(View view) {
        currentQuestion-= 1;
        if (currentQuestion<0)
        {
            //move forward to the end
            currentQuestion=totalQuestion;
        }
        question_txtView.setText(question_list.get(currentQuestion).getQuestion_content());
        setupListView(decodeAnswers(question_list.get(currentQuestion).getAnswer()));

    }
}