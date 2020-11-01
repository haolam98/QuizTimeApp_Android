package com.example.quiztimeapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quiztimeapp.feature_makequiz.MakeQuizActivity;
import com.example.quiztimeapp.feature_takequiz.TakeQuizActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuickAcessFragment extends Fragment {
    Button takeQuiz_bttn;
    Button makeQuiz_bttn;

    public QuickAcessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quick_acess, container, false);
        takeQuiz_bttn = v.findViewById(R.id.button_quickAccess_takeQuiz);
        makeQuiz_bttn = v.findViewById(R.id.button_quickAccess_makeQuiz);

        takeQuiz_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(QuickAcessFragment.class.getSimpleName(),"BUTTON CLICKED: Take Quiz button clicked --> GO TO TakeQuizActivity");
                Intent i = new Intent(getActivity(), TakeQuizActivity.class);
                startActivity(i);

            }
        });
        makeQuiz_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(QuickAcessFragment.class.getSimpleName(),"BUTTON CLICKED: Make Quiz button clicked --> GO TO MakeQuizActivity");
                Intent i = new Intent(getActivity(), MakeQuizActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

}
