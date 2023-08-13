package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    FirebaseAuth authProfile;

    private LottieAnimationView lottieAnimationView;
    private Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lottieAnimationView = findViewById(R.id.lottiehome);
        nextButton = findViewById(R.id.MainACtivityofApp);

        lottieAnimationView.setAnimation("covid_protection.json");
        lottieAnimationView.playAnimation();

        final boolean[] isButtonClicked = {false};
        Handler handler = new Handler();
        Runnable buttonRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isButtonClicked[0]) {
                    // Automatically navigate to the desired activity
                    if (authProfile.getCurrentUser() != null) {
                        startActivity(new Intent(Home.this, Homepage.class));
                        finish();
                    } else {
                        Intent intent = new Intent(Home.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation completed
                nextButton.setVisibility(View.VISIBLE);
                handler.postDelayed(buttonRunnable, 3000); // 3 seconds delay
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation cancelled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonClicked[0] = true;
                handler.removeCallbacks(buttonRunnable); // Cancel the automatic navigation
                // Handle button click, navigate to the other layout
                Intent intent = new Intent(Home.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });


        authProfile = FirebaseAuth.getInstance();

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
}