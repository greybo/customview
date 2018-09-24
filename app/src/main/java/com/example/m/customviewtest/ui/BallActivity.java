package com.example.m.customviewtest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.m.customviewtest.OtherViewActivity;
import com.example.m.customviewtest.R;
import com.example.m.customviewtest.views.BallView;

public class BallActivity extends AppCompatActivity {
    private static final String TAG = "BallView";
    private BallView ballView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        ballView = findViewById(R.id.ball_view);
        ballView.setOnClickListener(listener);
    }

    View.OnClickListener listener = v -> {
//        ballView.init();
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("other view");
        menu.add("restart");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "other view":
                startActivity(new Intent(this, OtherViewActivity.class));
                break;
            case "restart":
                ballView.reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
