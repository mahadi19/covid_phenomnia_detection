package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8, cv9,cv10,cv11;
    FirebaseAuth authProfile;

    int repetitionCount = 0;
    int maxRepetitions = 5;
    private LottieAnimationView lottieAnimationView,lottieAnimationView2,lottieAnimationView3,lottieAnimationView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);





        cv1= findViewById(R.id.Timeline);
        cv3= findViewById(R.id.Detect);
        cv4 = findViewById(R.id.General_Awareness);
        cv5= findViewById(R.id.LocationMap);
        cv6= findViewById(R.id.UploadPhototoNewsfeed);
        cv7 = findViewById(R.id.Logoutfromapp);
        cv8 = findViewById(R.id.UpdateUserNameProfile);
        cv9 = findViewById(R.id.UserProfileDisplay);
        cv10 = findViewById(R.id.Rateusid);
        cv11 = findViewById(R.id.Puzzle);


        lottieAnimationView = findViewById(R.id.lottiehomepage);

        lottieAnimationView2 = findViewById(R.id.lottiehome2);
        lottieAnimationView3 = findViewById(R.id.lottieratingbar);
         lottieAnimationView4 = findViewById(R.id.postOnyourTimeline);


        lottieAnimationView.setAnimation("covid_protection.json");
        lottieAnimationView.playAnimation();
        lottieAnimationView2.setAnimation("covid19.json");
        lottieAnimationView2.playAnimation();
        lottieAnimationView3.setAnimation("recent_post.json");
        lottieAnimationView3.playAnimation();
        lottieAnimationView4.setAnimation("write_post.json");
        lottieAnimationView4.playAnimation();


        authProfile = FirebaseAuth.getInstance();


        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,ShowImageReview.class);
                startActivity(intent);
            }
        });



        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation completed
                if (repetitionCount < maxRepetitions) {
                    repetitionCount++;
                    // Restart the animation
                    lottieAnimationView.playAnimation();
                } else {
                    // Animation completed all repetitions
                }

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

        lottieAnimationView2.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation completed
                if (repetitionCount < maxRepetitions) {
                    repetitionCount++;
                    // Restart the animation
                    lottieAnimationView2.playAnimation();
                } else {
                    // Animation completed all repetitions
                }
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
        lottieAnimationView3.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation completed
                if (repetitionCount < maxRepetitions) {
                    repetitionCount++;
                    // Restart the animation
                    lottieAnimationView3.playAnimation();
                } else {
                    // Animation completed all repetitions
                }
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
        lottieAnimationView4.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation completed
                if (repetitionCount < maxRepetitions) {
                    repetitionCount++;
                    // Restart the animation
                    lottieAnimationView4.playAnimation();
                } else {
                    // Animation completed all repetitions
                }
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
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,MainActivity.class);
                startActivity(intent);
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Awareness.class);
                startActivity(intent);
            }
        });

        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,MapActivity.class);
                startActivity(intent);
            }
        });

        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,ImageReview.class);
                startActivity(intent);
            }
        });
        cv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,ProfileUpdate.class);
                startActivity(intent);
            }
        });
        cv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,UserProfileActivity.class);
                startActivity(intent);
            }
        });
        cv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Rate_our_app.class);
                startActivity(intent);
            }
        });
        cv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,MultipleDropdownActivity.class);
                startActivity(intent);
            }
        });

        cv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutPressed();
            }
        });

    }

    private void onLogoutPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Confirm Logout !")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        authProfile.signOut();
                        Toast.makeText(Homepage.this, "Sign out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Homepage.this,Login.class);

                        //Clear stack to prevent userprofileactivity on pressing back button after loggging out
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate menu item
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When menu is selected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
//        if(id == R.id.menu_refresh){
//            //Refresh activity
//            startActivity(getIntent());
//            finish();
//            overridePendingTransition(0,0);
//        }
         if(id == R.id.menu_update_profile){
            Intent intent = new Intent(Homepage.this,ProfileUpdate.class);
            startActivity(intent);
        }

        else if(id == R.id.menu_update_email)
        {
            Intent intent = new Intent(Homepage.this,Homepage2.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_settings)
        {
            Toast.makeText(this, "Menu setting", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.menu_change_password)
       {
            Intent intent = new Intent(Homepage.this,ForgotPasswordActivity.class);
         startActivity(intent);
       }
        else if(id ==R.id.menu_delete_profile)
        {
            Intent intent = new Intent(Homepage.this,Rating_again.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_change_password)
        {
            Intent intent = new Intent(Homepage.this,ForgotPasswordActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_logout)
        {
            authProfile.signOut();
            Toast.makeText(Homepage.this, "Sign out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Homepage.this,Login.class);

            //Clear stack to prevent userprofileactivity on pressing back button after loggging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}