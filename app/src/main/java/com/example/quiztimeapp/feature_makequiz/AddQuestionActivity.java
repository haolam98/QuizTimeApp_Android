package com.example.quiztimeapp.feature_makequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quiztimeapp.R;
import com.example.quiztimeapp.adapters.listViewAdaper_forQuestions;
import com.example.quiztimeapp.adapters.listViewAdapter_forMultipleChoiceAns;
import com.example.quiztimeapp.objectClasses.Answer;
import com.example.quiztimeapp.objectClasses.question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.quiztimeapp.extra.REPLY_MESSAGE";
    //Share Preferences
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.quiztimeapp.feature_makequiz";

    int choosedType = -1;
    boolean answerDialog_isOn = false;
    TextView textView_quesType;
    EditText questionContent_txt;

    ArrayList<String> ans_list;
    ArrayList<Boolean> correct_ans_list;
    ArrayList<Answer> my_answers;
    ArrayList<question> ques_list;
    question my_question;

    //list view
    listViewAdapter_forMultipleChoiceAns my_adapter;
    ListView myList;
    private boolean exitActivity= false;

    /*
  ----- HANDLE LIFE-CYCLE FUNC :
   */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        myList = findViewById(R.id.list_multipleChoices_list);

        Log.d(AddQuestionActivity.class.getSimpleName(), "onCreate: AddQuestion Activity.... ");
        //question list
        ques_list = MakeQuizActivity.my_questions;

       // pop dialog choose question type
        popup_dialog_chooseQuesType();

        //init new question, list
        my_question = new question();
        ans_list = new ArrayList<String>();
        my_answers = new ArrayList<Answer>();
        correct_ans_list = new ArrayList<Boolean>();

        //set question_type to view
        textView_quesType = findViewById(R.id.textView_AddQuestion_Ques);
        questionContent_txt = findViewById(R.id.editTextText_questContent);

        //update question type
        updateQuesType();

        //setup list view
        //setupListView();
        //retrieve data from share preferences
        //retrieveDat_fromSharPref();


    }
    private void popup_dialog_chooseQuesType()
    {
        final Dialog myDialog = new Dialog(AddQuestionActivity.this);
        myDialog.setContentView(R.layout.dialog_choose_questype);

        Button mc_bttn = (Button) myDialog.findViewById(R.id.dialog_addAns_Ok_bttn);
        Button sa_bttn = (Button) myDialog.findViewById(R.id.chooseType_shAns_bttn) ;
        ImageButton cancel_bttn = (ImageButton) myDialog.findViewById(R.id.chooseType_cancel);
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // When buttons clicked...
        mc_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(AddQuestionActivity.class.getSimpleName(),"Multiple Choice Button is clicked...");
                choosedType= 1;
                updateQuesType();
                myDialog.cancel();
            }
        });
        sa_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(AddQuestionActivity.class.getSimpleName(),"Short Answer Button is clicked...");
                choosedType= 2;
                updateQuesType();
                myDialog.cancel();
            }
        });
        cancel_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MakeQuizActivity.class.getSimpleName(),"Cancel Button is clicked...");
                myDialog.cancel();

            }
        });
    }
    private void  updateQuesType()
    {
        //if type = 1 =>MC ; type = 2 =>SA
        if (choosedType ==1) {
            textView_quesType.setText(R.string.multiple_choice);
            my_question.setQuestion_type("Multiple Choice");
        }
        else if (choosedType ==2)
        {
            textView_quesType.setText(R.string.short_answer);
            my_question.setQuestion_type("Short Answer");
        }
    }
    /*
    @Override
    protected void onPause() {
        super.onPause();
        // onPause() will be called either :
        //  1. app go to background or pressed Back button
        //  2. finish() is called somewhere else (not in onCreate() )
        if (exitActivity==false) {
            Log.d(AddQuestionActivity.class.getSimpleName(), "ON PAUSE: saving data to SHARE PREFERENCES...");
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            //save data
            String content = questionContent_txt.getText().toString();
            preferencesEditor.putString("QUESTION_content",content );

            //apply json to save array list of questions
            Gson gson = new Gson();
            String json = gson.toJson(ans_list);
            preferencesEditor.putString("QUESTION_answer_list", json);
            String json2 = gson.toJson(correct_ans_list);
            preferencesEditor.putString("QUESTION_CORRECT_answer_list", json2);
            preferencesEditor.apply();
            Log.d(AddQuestionActivity.class.getSimpleName(), "ON PAUSE: Data is SAVED to SHARE PREFERENCES...");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(AddQuestionActivity.class.getSimpleName(), "ON RESUME: Retrieve data from SHARE PREFERENCES...");
        //retrieveDat_fromSharPref();
        Log.d(AddQuestionActivity.class.getSimpleName(), "ON RESUME: Retrieve data from SHARE PREFERENCES...COMPLETED");
    }
    private void retrieveDat_fromSharPref() {
        Log.d(AddQuestionActivity.class.getSimpleName(),"ON CREATE: Retrieving data from SHARE PREFERENCES...");

        //retrieve data
        String content = mPreferences.getString("QUESTION_content","");
        questionContent_txt.setText(content); //set to view

        //retrieve answer list & correct answer list
        Gson gson = new Gson();
        String json = mPreferences.getString("QUESTION_answer_list", "");
        Log.d(MakeQuizActivity.class.getSimpleName(),"json:  "+json);

        Gson gson2 = new Gson();
        String json2 = mPreferences.getString("QUESTION_CORRECT_answer_list", "");
        Log.d(MakeQuizActivity.class.getSimpleName(),"json2:  "+json);

        if(json2!="" && json!="") {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            Type type2 = new TypeToken<ArrayList<Boolean>>() {
            }.getType();

            ans_list = gson2.fromJson(json, type);
            correct_ans_list = gson.fromJson(json2, type2);
            //refresh list view
            setupListView();
        }
        else
        {
            Log.d(AddQuestionActivity.class.getSimpleName(),"ERROR: Cannot retrieve answer_list OR correct_answer_list");
        }

        Log.d(AddQuestionActivity.class.getSimpleName(),"ON CREATE: Retrieving data COMPLETED...");

    }*/

    private void setupListView() {

        Log.d(AddQuestionActivity.class.getSimpleName(),"SETUP LIST VIEW: Setting up list view...");

        Log.d(AddQuestionActivity.class.getSimpleName(),"list items: "+my_answers.size());

        /*if (correct_ans_list.isEmpty() == false)
        {//when there's something on the list
            //update correctness status
            correct_ans_list = listViewAdapter_forMultipleChoiceAns.answer_correctness;
            Log.d(AddQuestionActivity.class.getSimpleName(),"update correctness status ");

        }*/

        my_adapter = new listViewAdapter_forMultipleChoiceAns(this,R.layout.list_multiple_choice_item,my_answers);
        myList.setAdapter(my_adapter);
    }



    /*
    ----- HANDLE ON-CLICK BUTTON :
    */
    public void handleAddAnswer_clicked(View view) {
        Log.d(AddQuestionActivity.class.getSimpleName(),"ADD-ANSWER Button CLICKED: handling button clicked...");

        //popup message to add answer content
        Log.d(AddQuestionActivity.class.getSimpleName(),"POP-UP DIALOG: Popup dialog to add answer...");
        final Dialog myDialog = new Dialog(AddQuestionActivity.this);
        myDialog.setContentView(R.layout.dialog_add_answer_content);


        TextView title = (TextView)myDialog.findViewById(R.id.textView_dialog_addAns_title);
        final EditText answer = (EditText) myDialog.findViewById(R.id.editTextTextMultiLine_dialog_addAns_content);
        Button ok_bttn = (Button)myDialog.findViewById(R.id.dialog_addAns_Ok_bttn);
        Button cancel_bttn = (Button)myDialog.findViewById(R.id.dialog_addAns_cancel_bttn);

        //set title
        String tit = "Answer "+ ans_list.size()+1;
        title.setText(tit);

        ok_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(AddQuestionActivity.class.getSimpleName(),"Adding answer: "+ ans_list.size());
                String ans = answer.getText().toString();
                ans_list.add(ans);
                correct_ans_list.add(false); //set question correct ans to FALSE by default
                Log.d(AddQuestionActivity.class.getSimpleName(),"Adding answer: "+ ans_list.size());
                //add to my answer
                Answer a = new Answer(ans,false);
                my_answers.add(a);
                myDialog.cancel();
                setupListView();

            }
        });
        cancel_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get answer content from user input
                myDialog.cancel();
            }
        });
        myDialog.show();

    }


    public void handleCancelButton_clicked(View view) {
        Log.d(AddQuestionActivity.class.getSimpleName(),"CANCEL Button CLICKED: handling button clicked...");

        //popup warning mess
        Log.d(AddQuestionActivity.class.getSimpleName(),"POP-UP MESS: Popup warning mess...");
        final Dialog myDialog = new Dialog(AddQuestionActivity.this);
        myDialog.setContentView(R.layout.dialog_warning_mess);

        TextView title = (TextView)myDialog.findViewById(R.id.warning_title);
        TextView content = (TextView)myDialog.findViewById(R.id.warning_title);
        Button ok_bttn = (Button)myDialog.findViewById(R.id.dialog_warning_okBttn);
        Button cancel_bttn = (Button)myDialog.findViewById(R.id.dialog_warning_cancelBttn);

        content.setText(R.string.warning_mess);
        ok_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitActivity = true;
                myDialog.cancel();
            }
        });
        cancel_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get answer content from user input
                exitActivity=false;
                myDialog.cancel();
            }
        });
        if (exitActivity==true)
        {

            //exit
            finish();
        }
    }


    public  void popupMess_noAnswer()
    {
        final Dialog myDialog2 = new Dialog(AddQuestionActivity.this);
        myDialog2.setContentView(R.layout.dialog_warning_mess);

        TextView title2 = (TextView)myDialog2.findViewById(R.id.warning_title);
        TextView content2 = (TextView)myDialog2.findViewById(R.id.warning_title);
        Button ok_bttn2 = (Button)myDialog2.findViewById(R.id.dialog_warning_okBttn);
        Button cancel_bttn2 = (Button)myDialog2.findViewById(R.id.dialog_warning_cancelBttn);

        ok_bttn2.setText(R.string.ok_will_do_later);
        cancel_bttn2.setText(R.string.I_want_to_add_now);
        content2.setText(R.string.warning_mess2);
        ok_bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitActivity = true;
                myDialog2.cancel();
            }
        });
        cancel_bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get answer content from user input
                exitActivity=false;
                myDialog2.cancel();
            }
        });
    }
    public void handleAddDone_clicked(View view) {
        Log.d(AddQuestionActivity.class.getSimpleName(),"ADD-DONE Button CLICKED: handling button clicked...");

        Log.d(AddQuestionActivity.class.getSimpleName(),"ADD-DONE Button CLICKED: modifying answers...");

        //CASE 1: ANSWER >=1
        if (ans_list.size()>=1)
        {
            //modify answers
            String my_ans;

            //initialize header
            if (ans_list.size()==1)
            { my_ans = "\\\\[SA~1]\\\\"; }
            else {//multiple answers
                my_ans = "\\\\[MA~" + ans_list.size() + "]\\\\"; }
            //update correct_ans_list
            Log.d(AddQuestionActivity.class.getSimpleName(),"update the correctness of each answers...");
            Log.d(AddQuestionActivity.class.getSimpleName(),"BEFORE: "+correct_ans_list.toString());
            correct_ans_list = listViewAdapter_forMultipleChoiceAns.answer_correctness;
            Log.d(AddQuestionActivity.class.getSimpleName(),"AFTER: "+correct_ans_list.toString());

            //encode question
            for (int i = 0; i < ans_list.size(); i++) {
                my_ans += "\\\\"+ ans_list.get(i);
                if(correct_ans_list.get(i)==true)
                { my_ans +="\\\\[YES]\n\\\\"; }
                else { my_ans +="\\\\[NO]\n\\\\"; }
            }
            //add answers to question AND add question to question_list
            addAnswer_andQues(my_ans);

        }
        //CASE 2: NO ANSWER
        else if (ans_list.size()==0)
        {
            //pop warning dialog
            Log.d(AddQuestionActivity.class.getSimpleName(),"NO ANSWER IS ADDED -> POP-UP MESS: Popup warning mess...");
            popupMess_noAnswer();
            //check exitAcitity var again
            if (exitActivity==true)
            {
                //add answers to question AND add question to question_list
                addAnswer_andQues("N/A");

            }
        }
        //pass data back and exit
        returnData_andExit();


    }
    public void addAnswer_andQues(String my_ans)
    {
        //update question's content
        Log.d(AddQuestionActivity.class.getSimpleName(),"ADD QUESTION CONTENT: Adding answers to question...");

        my_question.setQuestion_content(questionContent_txt.getText().toString());
        Log.d(AddQuestionActivity.class.getSimpleName(),"ADD ANSWER: Adding answers to question...");
        //add answers to question
        my_question.setAnswer(my_ans);
        Log.d(AddQuestionActivity.class.getSimpleName(),"ADD ANSWER: "+my_ans);

    }

    private void returnData_andExit()
    {
        Log.d(AddQuestionActivity.class.getSimpleName(),"PASS DATA BACK: Passing data back to MakeQuizActivity...");

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, (Serializable) my_question);
        setResult(RESULT_OK,replyIntent);
        finish();
    }


    public void handleAttachment_clicked(View view) {

    }
}