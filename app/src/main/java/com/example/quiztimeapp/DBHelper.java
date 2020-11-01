package com.example.quiztimeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.quiztimeapp.objectClasses.Quiz;
import com.example.quiztimeapp.objectClasses.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //NAME OF DATABASE
    public static final String DATABASE_NAME = "local_user_storage.db";

    //declare table name: User_quiz_table
    public static  final  String TBL_USER_QUIZ = "User_Quiz_Info_table";
    public static  final  String QUIZ_ID = "QuizId";
    public static  final  String TITLE = "Title";
    public static  final  String DATE = "dateCreated";
    public static  final  String TOTAL_QUES = "totalQuestion";
    public static  final  String ATTACHMENT = "Attachment";
    public static  final  String DES = "Description";

    private String TBL_CREATE_USER_QUIZ = "create table " + TBL_USER_QUIZ + " ("+
            QUIZ_ID + " integer primary key autoincrement," +
            TITLE + " text,"+
            DATE + " text,"+
            DES + " text,"+
            ATTACHMENT + " text,"+
            TOTAL_QUES + " integer)";


    //declare table name: question_table
    public static  final  String TBL_QUESTION = "Question_table";
    public static  final  String QUESTION_ID = "QuestionId";
    //public static  final  String QUIZ_ID = "QuizId";
    public static  final  String QUESTION = "question";
    public static  final  String ANSWER = "answer";
    public static  final  String TYPE = "type";

    private String TBL_CREATE_QUIZ_QUESTION = "create table " + TBL_QUESTION + " ("+
            QUESTION_ID + " integer primary key autoincrement," +
            QUIZ_ID + " integer," +
            TYPE + " text,"+
            QUESTION + " text,"+
            ANSWER + " text,"+
            ATTACHMENT + " text)";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        // SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(TBL_CREATE_USER_QUIZ);
        db.execSQL(TBL_CREATE_QUIZ_QUESTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update table if have any change
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_USER_QUIZ); // drop means delete
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_QUESTION); // drop means delete
        onCreate(db);
    }

// ***********   FOR TABLE : USER QUIZ INFO TABLE **********

    public boolean insert_new_quiz_info (String title, String date, String description, int totalQues, String attach)
    {
        //Create instance of database
        SQLiteDatabase db = this.getWritableDatabase();

        //Create instance of class ContentValue
        ContentValues contentValues= new ContentValues();

        contentValues.put(TITLE,title);
        contentValues.put(DATE,date);
        contentValues.put(ATTACHMENT,attach);
        contentValues.put(DES,description);
        contentValues.put(TOTAL_QUES,totalQues);

        //if data is not insert, method return -1. else return the row
        long result = db.insert(TBL_USER_QUIZ,null,contentValues);
        if (result==-1)
            return false;
        return true;

    }

    public Cursor getAll_Quiz_Data ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TBL_USER_QUIZ, null); // * means take all
        return res;

    }
    public boolean update_Quiz_info (int quiz_id, String title, String date, String attach, String des, int totalQues)
    {
        //Create instance of database
        SQLiteDatabase db = this.getWritableDatabase();

        //Create instance of class ContentValue
        ContentValues contentValues= new ContentValues();

        contentValues.put(QUIZ_ID,quiz_id);
        contentValues.put(TITLE,title);
        contentValues.put(DATE,date);
        contentValues.put(ATTACHMENT,attach);
        contentValues.put(DES,des);
        contentValues.put(TOTAL_QUES,totalQues);

        db.update(TBL_USER_QUIZ, contentValues, "QuizId ="+quiz_id, null);
        return true;
    }

    public Integer delete_Quiz_info(int quiz_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // return number of row are deleted, if no row is deleted, return 0
        // delete questions related to the quiz

        return db.delete(TBL_USER_QUIZ,"QuizId = "+quiz_id, null);


    }

    public ArrayList<String> getAll_Quiz_Titles() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TBL_USER_QUIZ, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(TITLE))); // index of column is from 0 to...
            res.moveToNext();
        }
        return array_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Quiz> getAll_Quiz_Collection ()
    {
        ArrayList<Quiz> array_list = new ArrayList<Quiz>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TBL_USER_QUIZ, null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            int quizId = res.getInt(res.getColumnIndex(QUIZ_ID));
            String title = res.getString(res.getColumnIndex(TITLE));
            String date = res.getString(res.getColumnIndex(DATE));
            String attach = res.getString(res.getColumnIndex(ATTACHMENT));
            String des = res.getString(res.getColumnIndex(DES));
            int total = res.getInt(res.getColumnIndex(TOTAL_QUES));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

            Quiz newset = new Quiz(title, des, attach,date, total,quizId );
            array_list.add(newset);
            res.moveToNext();
        }
        return array_list;
    }

    public int getCurrent_Quiz_ID ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TBL_USER_QUIZ, null );
        res.moveToLast();
        //return res.getString(res.getColumnIndex(SET_ID));
        return res.getInt(res.getColumnIndex(QUIZ_ID));
    }

    public void removeAll_Quizzes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // return number of row are deleted, if no row is deleted, return 0
        db.delete(TBL_USER_QUIZ,null, null);
    }


// ***********   FOR TABLE :  QUESTION TABLE **********

    public boolean insert_new_question (int quiz_id, question question)
    {
        //Create instance of database
        SQLiteDatabase db = this.getWritableDatabase();

        //Create instance of class ContentValue
        ContentValues contentValues= new ContentValues();

        contentValues.put(QUIZ_ID,quiz_id);
        contentValues.put(QUESTION,question.getQuestion_content());
        contentValues.put(ANSWER,question.getAnswer());
        if (question.getImageAttachment()!=null)
        {
            contentValues.put(ATTACHMENT,question.getImageAttachment());
        }
        else
        {
            contentValues.put(ATTACHMENT,"N/A");
        }

        contentValues.put(TYPE,question.getQuestion_type());


        //if data is not insert, method return -1. else return the row
        long result = db.insert(TBL_QUESTION,null,contentValues);
        if (result==-1)
            return false;
        return true;

    }

    public Cursor getAll_Question_Data ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TBL_QUESTION, null); // * means take all
        return res;

    }
    public boolean update_question_info (int question_id, int quiz_id, String question, String answer, String attach, String type)
    {
        //Create instance of database
        SQLiteDatabase db = this.getWritableDatabase();

        //Create instance of class ContentValue
        ContentValues contentValues= new ContentValues();

        contentValues.put(QUESTION_ID,question_id);
        contentValues.put(QUIZ_ID,quiz_id);
        contentValues.put(ANSWER,answer);
        contentValues.put(QUESTION,question);
        contentValues.put(ATTACHMENT,attach);
        contentValues.put(TYPE,type);

        db.update(TBL_QUESTION, contentValues, "CardId ="+ question_id, null);
        return true;
    }

    public Integer delete_question_byQuesID(String question_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // return number of row are deleted, if no row is deleted, return 0
        return db.delete(TBL_QUESTION,"QuestionId ="+question_id, null);

    }
    public Integer delete_question_byQuizID(String quiz_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // return number of row are deleted, if no row is deleted, return 0
        return db.delete(TBL_QUESTION,"QuizId ="+quiz_id, null);

    }
    public void removeAll_Questions()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // return number of row are deleted, if no row is deleted, return 0
        db.delete(TBL_QUESTION,null, null);
    }

    public ArrayList<question> getAll_Questions ()
    {
        ArrayList<question> array_list = new ArrayList<question>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TBL_QUESTION, null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            int quiz_id = res.getInt(res.getColumnIndex(QUIZ_ID));
            int question_Id = res.getInt(res.getColumnIndex(QUESTION_ID));
            String ques = res.getString(res.getColumnIndex(QUESTION));
            String ans = res.getString(res.getColumnIndex(ANSWER));
            String attach = res.getString(res.getColumnIndex(ATTACHMENT));
            String type = res.getString(res.getColumnIndex(TYPE));

            question newques = new question();
            array_list.add(newques);
            res.moveToNext();
        }
        return array_list;
    }


}
