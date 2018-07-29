package com.example.m.customviewtest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagesAdapter pagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pageView);

        pagesAdapter = new PagesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagesAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("ball view");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "ball view":
                startActivity(new Intent(this, BallActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class PagesAdapter extends FragmentPagerAdapter {

        public PagesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new Page1Fragment();
                case 1:
                    return new Page2Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
