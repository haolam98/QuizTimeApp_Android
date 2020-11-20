package com.example.quiztimeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quiztimeapp.R;
import com.example.quiztimeapp.feature_makequiz.AddQuestionActivity;
import com.example.quiztimeapp.objectClasses.Answer;
import com.example.quiztimeapp.objectClasses.question;

import java.util.ArrayList;
import java.util.List;

public class listViewAdapter_forMultipleChoiceAns extends ArrayAdapter<Answer> {
    private List<Answer> answers;
    public static ArrayList<Boolean> answer_correctness;
    private Activity context;


    public listViewAdapter_forMultipleChoiceAns(@NonNull Activity context, int resource, @NonNull ArrayList<Answer> objects) {
        super(context, resource, objects);
        this.answers= objects;
        this.context = context;
        answer_correctness = new ArrayList<Boolean>();
        for (int i =0; i<objects.size(); i++)
        {
            answer_correctness.add(objects.get(i).getCorrect());
        }

    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_multiple_choice_item,null,true);
        TextView content = view.findViewById(R.id.textView_list_multipleChoice_content_item);
        RadioButton radioButton = view.findViewById(R.id.radioButton_list_multiple_choice_item);

        Answer a = answers.get(position);
        content.setText(a.getAsnwer_content());


        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(listViewAdapter_forMultipleChoiceAns.class.getSimpleName(),"Answer Item CLICKED: item= "+position);
                Log.d(listViewAdapter_forMultipleChoiceAns.class.getSimpleName(),"Answer Item CLICKED: change answer's correctness status...");
                answer_correctness.set(position,isChecked);
            }
        });
        return view;
    }
}
