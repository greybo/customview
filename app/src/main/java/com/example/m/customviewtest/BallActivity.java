package com.example.m.customviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.m.customviewtest.views.BallView;

public class BallActivity extends AppCompatActivity {
    private static final String TAG = "BallView";
    private BallView ballView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        ballView = findViewById(R.id.ball_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("other view");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "other view":
                startActivity(new Intent(this, OtherViewActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
