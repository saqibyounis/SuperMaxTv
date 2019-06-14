package com.kamel.kameltv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View easySplashScreenView = new EasySplashScreen(SplashActvity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundResource(android.R.color.black)
                .withHeaderText("Royalty Tv")
                .withFooterText("Copyright 2016")

                .withLogo(R.drawable.rotlogob)
                .create();

        setContentView(easySplashScreenView);
    }
}
