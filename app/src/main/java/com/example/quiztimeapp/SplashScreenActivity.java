package com.example.quiztimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainPageActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                .withBeforeLogoText("WELCOME TO QUIZ TIME APP")
                .withFooterText("Designed and Developed By Hao Lam")
                .withLogo(R.mipmap.quiz_time_icon);
        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        config.getBeforeLogoTextView().setTextSize(20);
        config.getFooterTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextSize(10);

        View ESScreen = config.create();
        setContentView(ESScreen);

    }
}
