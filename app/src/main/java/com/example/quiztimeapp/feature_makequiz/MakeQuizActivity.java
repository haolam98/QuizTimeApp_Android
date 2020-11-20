package com.example.quiztimeapp.feature_makequiz;

import androidx.annotation.Nullable;
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

public class MakeQuizActivity extends AppCompatActivity {

    //for passing data back&forth
    public static final String EXTRA_MESSAGE = "com.example.quiztimeapp.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;

    private boolean exitActivity= false;
    static int choosedType= -1;
    static boolean isDeleted= false;
    static boolean isBackFromAdd_activity = false;
    question myNewQuestion;

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
    /*
    ----- HANDLE LIFE-CYCLE FU :
    */
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

        //hook listView
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

    //Receiving data from other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(MakeQuizActivity.class.getSimpleName(), "OnActivityResult: Retrieve question's content from AddQuestionActicvity...");

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                //retrieve data send from other activity
                myNewQuestion = new question();
                myNewQuestion = (question) data.getSerializableExtra(AddQuestionActivity.EXTRA_REPLY);
                isBackFromAdd_activity = true;
                Log.d(MakeQuizActivity.class.getSimpleName(), "OnActivityResult: Add new_q to questions list:  "+myNewQuestion.toString());

            }
        }

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
            if (isBackFromAdd_activity==true)
            {
                //add new question
                my_questions.add(myNewQuestion);
                //reset bool
                isBackFromAdd_activity = false;
            }
            //refresh list view
            setupListView();
        }

        Log.d(MakeQuizActivity.class.getSimpleName(),"ON CREATE: Retrieving data COMPLETED...");

    }
    private void setupListView() {
        Log.d(MakeQuizActivity.class.getSimpleName(),"SETUP LIST VIEW: Setting up list view...");
        Log.d(MakeQuizActivity.class.getSimpleName(),"list items: "+my_questions.size());

        my_adapter = new listViewAdaper_forQuestions(this,R.layout.questions_row, my_questions);
        myList.setAdapter(my_adapter);
    }


/*
----- HANDLE ON-CLICK LISTENER:
*/
    private void addBttn_Clicked() {
        // go to activity
        Log.d(MakeQuizActivity.class.getSimpleName(),"GO TO ACTIVITY : addQuestion activity");

        Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
        //go to activity
        startActivityForResult(intent,TEXT_REQUEST);
    }

    private void handleItemClicked(final int position) {

        //popup message which show content of the selected question
        Log.d(MakeQuizActivity.class.getSimpleName(),"POP-UP MESS: Popup selected item question's content...");
        final Dialog myDialog = new Dialog(MakeQuizActivity.this);
        myDialog.setContentView(R.layout.dialog_show_question_content);

        TextView question = (TextView)myDialog.findViewById(R.id.DialogShowQuestion_questionContent);
        TextView answer = (TextView)myDialog.findViewById(R.id.DialogShowQuestion_answer);
        TextView type = (TextView)myDialog.findViewById(R.id.DialogShowQuestion_type);
        TextView attch = (TextView)myDialog.findViewById(R.id.DialogShowQuestion_attch);
        ImageView image = (ImageView) myDialog.findViewById(R.id.DialogShowQuestion_image);
        Button ok_bttn = (Button)myDialog.findViewById(R.id.DialogShowQuestion_ok);
        Button del_bttn = (Button)myDialog.findViewById(R.id.DialogShowQuestion_delete);

        question q = my_questions.get(position);
        if (q.getImageAttachment()=="" || q.getImageAttachment()=="N/A"||q.getImageAttachment()==null)
        {
            //make image part invisible
            attch.setVisibility(View.INVISIBLE);
            image.setVisibility(View.INVISIBLE);
        }
        else
        {
            //set image
        }
        String q_content = decode_questionContent(q.getQuestion_content());

        question.setText(q_content);
        answer.setText(q.getAnswer());
        type.setText(q.getQuestion_type());
        myDialog.show();
        //Ok button clicked!
        ok_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeleted = false;
                myDialog.cancel();
            }
        });
        del_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MakeQuizActivity.class.getSimpleName(), "onClickItem - Delete Clicked: deleting item: "+position);
                isDeleted = true;
                deleteItem(position);
                myDialog.cancel();
            }
        });



    }
    private  void deleteItem(int position)
    {
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
        //reset isDeleted
        isDeleted=false;

    }

    private String decode_questionContent(String question_content) {

        return question_content;
    }

    public void quizMaking_doneClicked(View view)
    {
        //save draft to DB
        saveDat_toDB();
        //pop up message

        //go to preview quiz activity
        Log.d(MakeQuizActivity.class.getSimpleName(),"GO TO ACTIVITY : preview activity");
        Intent intent = new Intent(getApplicationContext(), PreviewQuizActivity.class);

        //gather quiz info + questions list
        int id = myDB.getCurrent_Quiz_ID();
       // Quiz new_quiz = new Quiz( title,  des,  "N/A",  mdate, total_question, id);
        intent.putExtra("QUESTION_TOTAL",total_question);
        intent.putExtra("QUESTION_LIST",my_questions);
        //go to activity
        startActivity(intent);
    }

    public void quizMaking_DraftClicked(View view) {
        //save draft to DB
        saveDat_toDB();
        //reset shared preference
        reset();
        // pop up message
        popup_saveMess();
        finish();


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