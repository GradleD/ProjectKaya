package com.durvasa.projectkaya;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    TextView t1;
    Button btnSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RunAnimation();
        t1 =findViewById(R.id.splashtext);
        btnSplash = findViewById(R.id.btnSplash);
        btnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                //finish();
            }
        });

    }
    private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.textanim);
        a.reset();
        TextView tv = findViewById(R.id.splashtext);
        tv.clearAnimation();
        tv.startAnimation(a);
    }
}

