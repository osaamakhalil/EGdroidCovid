package com.example.covid.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;


import com.airbnb.lottie.LottieAnimationView;
import com.example.covid.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_flash)
    LottieAnimationView splashFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        startCheckAnimationLogo();
    }

    private void startCheckAnimationLogo() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        animator.addUpdateListener(animation -> splashFlash.setProgress((Float) animation.getAnimatedValue()));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                super.onAnimationEnd(animation);
            }
        });
        if (splashFlash.getProgress() == 0f) {
            animator.start();
        } else {
            splashFlash.setProgress(0f);
        }
    }
}

