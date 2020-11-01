package com.example.quiztimeapp.feature_makequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quiztimeapp.DBHelper;
import com.example.quiztimeapp.R;
import com.example.quiztimeapp.adapters.listViewAdaper_forQuestions;
import com.example.quiztimeapp.objectClasses.Quiz;
import com.example.quiztimeapp.objectClasses.question;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeQuizActivity extends AppCompatActivity {

    private boolean exitActivity= false;
    static int choosedType= -1;

    //Share Preferences
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.quiztimeapp.feature_makequiz";

    //SQLlite Database
    private DBHelper myDB;

    EditText title_txt;
    EditText description_txt;
    Button done_bttn;
    FloatingActionButton addQuestion_bttn;
    TextView totalQues_textView;
    ListView myList;
    Button mc_bttn; //multiple choice
    Button sa_bttn; //short ans

    private int total_question= 0;
    private String title="";
    private String des="";
    private String mdate="";
    public static ArrayList<question> my_questions;

    listViewAdaper_forQuestions my_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_quiz);
        //initial share perferences
        mPreferences = getSharedPreferences(
                sharedPrefFile, MODE_PRIVATE);

        //get user's input from view
        title_txt = findViewById(R.id.editText_makeQuiz_title);
        description_txt= findViewById(R.id.editText_makeQuiz_description);
        addQuestion_bttn = findViewById(R.id.floatingActionButton_makeQuiz_addQuestion);
        totalQues_textView = findViewById(R.id.textView_makeQuiz_totalQuestion);
        done_bttn = findViewById(R.id.button_makeQuiz_Done);
        myList = findViewById(R.id.list_makeQuiz_questionList);

        //Initialize values and obj
        myDB = new DBHelper(this);
        my_questions = new ArrayList<question>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        mdate = formatter.format(date);

        total_question = my_questions.size();
        totalQues_textView.setText(String.valueOf(total_question));
        title_txt.setText(title);
        description_txt.setText(des);

        //retrieve data from share preferences
        retrieveDat_fromSharPref();

        //add button clicked
        addQuestion_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBttn_Clicked();
            }
        });
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(MakeQuizActivity.class.getSimpleName(),"ON QUESTION LIST: Item "+position+" is clicked!");
                handleItemClicked(position);
            }
        });
    }



    private void setupListView() {
        Log.d(MakeQuizActivity.class.getSimpleName(),"SETUP LIST VIEW: Setting up list view...");
        Log.d(MakeQuizActivity.class.getSimpleName(),"list items: "+my_questions.size());

        my_adapter = new listViewAdaper_forQuestions(this,R.layout.questions_row, my_questions);
        myList.setAdapter(my_adapter);
    }

    private void retrieveDat_fromSharPref() {
        Log.d(MakeQuizActivity.class.getSimpleName(),"ON CREATE: Retrieving data from SHARE PREFERENCES...");

        //retrieve data
        title = mPreferences.getString("QUIZ_title","");
        des = mPreferences.getString("QUIZ_des","");
        total_question = mPreferences.getInt("QUIZ_total_question",0);
        //set to view
        title_txt.setText(title);
        description_txt.setText(des);
        totalQues_textView.setText(String.valueOf(total_question));

        Gson gson = new Gson();
        String json = mPreferences.getString("QUIZ_list_ques", "");
        Log.d(MakeQuizActivity.class.getSimpleName(),"json:  "+json);
        if(json!="") {
            Type type = new TypeToken<ArrayList<question>>() {
            }.getType();
            my_questions = gson.fromJson(json, type);
            //refresh list view
            setupListView();
        }

        Log.d(MakeQuizActivity.class.getSimpleName(),"ON CREATE: Retrieving data COMPLETED...");

    }
    private void handleItemClicked(int position) {
        boolean isDeleted = false;
        //popup message which show content of the selected question

        //if deleted button is clicked ->delete item from list
        if (isDeleted==true)
        {
            my_questions.remove(position);
            //update total questions
            total_question-= 1;
            totalQues_textView.setText(String.valueOf(total_question));
            //update list view again
            setupListView();
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        // onPause() will be called either :
        //  1. app go to background or pressed Back button
        //  2. finish() is called somewhere else (not in onCreate() )
        if (exitActivity==false) {
            Log.d(MakeQuizActivity.class.getSimpleName(), "ON PAUSE: saving data to SHARE PREFERENCES...");
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            //save data
            total_question = my_questions.size();
            preferencesEditor.putInt("QUIZ_total_question", total_question);
            preferencesEditor.putString("QUIZ_title", title_txt.getText().toString());
            preferencesEditor.putString("QUIZ_des", description_txt.getText().toString());
            preferencesEditor.putString("QUIZ_date", mdate);

            //apply json to save array list of questions
            Gson gson = new Gson();
            String json = gson.toJson(my_questions);
            preferencesEditor.putString("QUIZ_list_ques", json);
            preferencesEditor.apply();
            Log.d(MakeQuizActivity.class.getSimpleName(), "ON PAUSE: Data is SAVED to SHARE PREFERENCES...");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MakeQuizActivity.class.getSimpleName(), "ON RESUME: Retrieve data from SHARE PREFERENCES...");
        retrieveDat_fromSharPref();
        Log.d(MakeQuizActivity.class.getSimpleName(), "ON RESUME: Retrieve data from SHARE PREFERENCES...COMPLETED");
    }
/*
----- HANDLE ON-CLICK LISTENER:
*/
    private void addBttn_Clicked() {
        //pop-up dialog to choose question type
        Log.d(MakeQuizActivity.class.getSimpleName(),"POP-UP MESS: Ask user type of question to add...");
        final Dialog myDialog = new Dialog(MakeQuizActivity.this);
        myDialog.setContentView(R.layout.dialog_choose_questype);

        mc_bttn = (Button) myDialog.findViewById(R.id.chooseType_mulCh_bttn);
        sa_bttn = (Button) myDialog.findViewById(R.id.chooseType_shAns_bttn) ;
        ImageButton cancel_bttn = (ImageButton) myDialog.findViewById(R.id.chooseType_cancel);
        myDialog.show();
        // When buttons clicked...
        mc_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MakeQuizActivity.class.getSimpleName(),"Multiple Choice Button is clicked...");
                choosedType= 1;
                myDialog.cancel();
            }
        });
        sa_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MakeQuizActivity.class.getSimpleName(),"Short Answer Button is clicked...");
                choosedType= 2;
                myDialog.cancel();
            }
        });
        cancel_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MakeQuizActivity.class.getSimpleName(),"Cancel Button is clicked...");
                //reset
                choosedType= -1;
                myDialog.cancel();
            }
        });

        // go to activity
        Log.d(MakeQuizActivity.class.getSimpleName(),"User choice (choosedType): "+choosedType);
        if (choosedType!=-1)
        {
            if (choosedType==1)
            {
                // multiple choice
                Log.d(MakeQuizActivity.class.getSimpleName(),"Go to multiple choice Activity...");
            }
            else if (choosedType==2)
            {
                //short answer
                Log.d(MakeQuizActivity.class.getSimpleName(),"Go to Short Answer Activity...");


            }
            //reset choosedType to -1
            choosedType=-1;
        }
        else
        {
            Log.d(MakeQuizActivity.class.getSimpleName(),"User clicked cancel button-> DO NOTHING!");
        }
    }


    public void quizMaking_doneClicked(View view)
    {
        //save draft to DB

        //pop up message

        //reset shared preference data save

        //go to preview quiz activity

    }

    public void quizMaking_DraftClicked(View view) {
        //save draft to DB
        saveDat_toDB();
        //reset shared preference
        reset();
        // pop up message
        popup_saveMess();


    }


    private void saveDat_toDB()
    {
        Log.d(MakeQuizActivity.class.getSimpleName(),"SAVE DATA: saving data to DATABASE...");
        //save quiz's info
        myDB.insert_new_quiz_info(title,mdate,des,total_question,"N/A");
        int currentQuizID = myDB.getCurrent_Quiz_ID();
        //save questions
        for (int i=0; i<my_questions.size(); i++)
        {
            myDB.insert_new_question(currentQuizID,my_questions.get(i));
        }
        Log.d(MakeQuizActivity.class.getSimpleName(),"SAVE DATA: SUCCESSFULLY saved data to DATABASE...");

    }

    private void popup_saveMess() {
        Log.d(MakeQuizActivity.class.getSimpleName(),"POP-UP MESS: Notify User that the Quiz is saved to local DB...");
        final Dialog myDialog = new Dialog(MakeQuizActivity.this);
        myDialog.setContentView(R.layout.dialog_saved_local);
        Button ok_bttn = (Button)myDialog.findViewById(R.id.savedLocal_okBttn);
        myDialog.show();
        //Ok button clicked!
        ok_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                //exit activity (go back to main)
                exitActivity= true;
                finish();
            }
        });
    }
    private void reset() {
        Log.d(MakeQuizActivity.class.getSimpleName(),"RESET DATA: Clear list & share preference data...");
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply(); //apply change
        //empty the list,title, des
        my_questions.clear();
        Log.d(MakeQuizActivity.class.getSimpleName(),"my_question list size: "+ my_questions.size());
        Log.d(MakeQuizActivity.class.getSimpleName(),"RESET DATA: Clear list & share preference data...COMPLETED");

    }

}