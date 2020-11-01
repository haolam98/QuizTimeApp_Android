package com.example.quiztimeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quiztimeapp.R;
import com.example.quiztimeapp.objectClasses.question;

import java.util.ArrayList;
import java.util.List;

public class listViewAdaper_forQuestions extends ArrayAdapter<question> {
    private List<question> questions;
    private Activity context;
    public listViewAdaper_forQuestions(@NonNull Activity context, int resource, @NonNull ArrayList<question> objects) {
        super(context, resource, objects);
        this.questions = objects;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.questions_row,null,true);
        TextView title = view.findViewById(R.id.questionRow_text);
        TextView type = view.findViewById(R.id.questionRow_subText);
        ImageView image = view.findViewById((R.id.questionRow_icon));

        question q = questions.get(position);
        title.setText(q.getQuestion_content());
        type.setText(q.getQuestion_type());
        image.setImageResource(R.drawable.ic_question_purple_24);
        return view;
    }
}
