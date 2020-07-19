package com.exemple.enjoyfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import dz.baichoudjedi.lovefood.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPALSH_SCREEN_TIMEOUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        // Rediriger vers la page principale "MainActivity" après 3 secondes.
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                //démarer une page
                Intent intent = new Intent(getApplicationContext(),IntroActivity.class);
                startActivity(intent);
                finish();
            }
        };

        // handler post delayed
        new Handler().postDelayed(runnable,SPALSH_SCREEN_TIMEOUT);
    }
}
