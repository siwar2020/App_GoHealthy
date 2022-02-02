package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SecondActivity extends AppCompatActivity {
    ViewPager mViewPager;
    LinearLayout mDots;
    SliderAdapter mSliderAdapter;
    TextView[] dots;
    Button lets_get_started;
    Animation mAnimation;
    int CurrentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);

        mViewPager = findViewById(R.id.slider);
        mDots = findViewById(R.id.dots);
        lets_get_started = findViewById(R.id.get_started);

        mSliderAdapter = new SliderAdapter(this);
        mViewPager.setAdapter(mSliderAdapter);
        addDots(0);
        mViewPager.addOnPageChangeListener(changeListener);

    }
    public void skip(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
    public void next(View view){
        mViewPager.setCurrentItem(CurrentPos + 1);
    }

    private void addDots(int position) {
        dots = new TextView[3];
        mDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            mDots.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.rouge));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            CurrentPos=position;
            addDots(position);
            if (position == 0) {
                lets_get_started.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                lets_get_started.setVisibility(View.INVISIBLE);

            } else {
                mAnimation= AnimationUtils.loadAnimation(SecondActivity.this, R.anim.side_animation);
                lets_get_started.setAnimation(mAnimation);
                lets_get_started.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}