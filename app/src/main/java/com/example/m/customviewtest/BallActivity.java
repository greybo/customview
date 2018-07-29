package com.example.m.customviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.m.customviewtest.views.BallView;

public class BallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        BallView ballView = findViewById(R.id.ball_view);
    }
}
