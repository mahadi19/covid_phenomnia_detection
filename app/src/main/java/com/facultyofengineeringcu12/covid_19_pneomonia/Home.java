package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
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
        getSupportActionBar().setTitle("Welcome");





        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {

            Toast.makeText(this, "Please make sure you have an internet connection", Toast.LENGTH_LONG).show();

// Delay the transition to another activity after the toast is finished
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Home.this, UserProfilePicUploadActivity.class);
                    startActivity(intent);
                }
            }, 10000); // 10000 milliseconds (10 seconds)

        } else {

            final boolean[] isButtonClicked = {false};
            Handler handler = new Handler();
            Runnable buttonRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!isButtonClicked[0]) {
                        // Automatically navigate to the desired activity
                        if (authProfile.getCurrentUser() != null) {
                            startActivity(new Intent(Home.this, Homepage2.class));
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
            lottieAnimationView.setAnimation("covid_protection.json");
            lottieAnimationView.playAnimation();
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isButtonClicked[0] = true;
                    handler.removeCallbacks(buttonRunnable); // Cancel the automatic navigation
                    // Handle button click, navigate to the other layout
                    Intent intent = new Intent(Home.this, Homepage2.class);
                    startActivity(intent);
                    finish();
                }
            });
            authProfile = FirebaseAuth.getInstance();
            // Proceed with the normal functionality of the activity
        }














    }
    @Override
    protected void onStart() {
        super.onStart();

    }
}