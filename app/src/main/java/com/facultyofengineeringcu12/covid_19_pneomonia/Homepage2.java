package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Homepage2 extends AppCompatActivity {
    FirebaseAuth authProfile;
    TextView  cvDetectTxt;
    ImageView cvDetect;


     CardView  cvProfile,cvHome,cvUpdate,cvTimeline,cvPost,cvProfile2,cvDet,cvMap,cvAware,cvDrop,cvRate,cvLogout,cvShareapp,cvNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage2);
        getSupportActionBar().setTitle("Dashboard");

        cvProfile = findViewById(R.id.cardView1);
        cvHome = findViewById(R.id.cardView2);
        cvUpdate = findViewById(R.id.cardView3);
        cvDetect = findViewById(R.id.DetectDiseaseCovid);
        cvDetectTxt = findViewById(R.id.DetectDiseaseCOVIDText);
        cvTimeline = findViewById(R.id.cardViewL1);
        cvPost =    findViewById(R.id.cardViewL2);
        cvProfile2 = findViewById(R.id.cardViewL3);
        cvDet = findViewById(R.id.cardViewL4);
        cvMap = findViewById(R.id.cardViewL5);
        cvAware = findViewById(R.id.cardViewL6);
        cvDrop = findViewById(R.id.cardViewL7);
        cvRate = findViewById(R.id.cardViewL8);
        cvLogout = findViewById(R.id.cardView11);
        cvShareapp= findViewById(R.id.cardView10);
        cvNew = findViewById(R.id.cardView9);

        authProfile = FirebaseAuth.getInstance();


        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.covidimage6);
        images.add(R.drawable.covidimages8);
        images.add(R.drawable.covidimage3);
        images.add(R.drawable.covidimage5);
        images.add(R.drawable.covidimage2);


        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(images);
        viewPager.setAdapter(adapter);


        cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,UserProfileActivity.class);
                startActivity(intent);

            }
        });
        cvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,Homepage.class);
                startActivity(intent);

            }
        });
        cvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,ProfileUpdate.class);
                startActivity(intent);

            }
        });
        cvDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,MainActivity.class);
                startActivity(intent);

            }
        });
        cvDetectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,MainActivity.class);
                startActivity(intent);

            }
        });
        cvTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,ShowImageReview.class);
                startActivity(intent);

            }
        });
        cvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,ImageReview.class);
                startActivity(intent);

            }
        });
        cvProfile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,UserProfileActivity.class);
                startActivity(intent);

            }
        });
        cvDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,ShowImageReview.class);
                startActivity(intent);

            }
        });
        cvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,MapActivity.class);
                startActivity(intent);

            }
        });
        cvAware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,Awareness.class);
                startActivity(intent);

            }
        });
        cvDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,MultipleDropdownActivity.class);
                startActivity(intent);

            }
        });
        cvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage2.this,Rate_our_app.class);
                startActivity(intent);

            }
        });
        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onLogoutPressed();

            }
        });

        cvShareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Homepage2.this, "In progress", Toast.LENGTH_SHORT).show();

                /*
                // Get the path to the APK file
                String apkFilePath = getApplicationInfo().sourceDir;

                // Create a temporary file to copy the APK
                File tempFile = new File(getCacheDir(), "app.apk");

                try {
                    // Copy the APK to the temporary file
                    FileInputStream inputStream = new FileInputStream(new File(apkFilePath));
                    FileOutputStream outputStream = new FileOutputStream(tempFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    inputStream.close();
                    outputStream.close();

                    // Generate a content URI for the temporary file
                    Uri tempUri = FileProvider.getUriForFile(Homepage2.this,
                            BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);

                    // Create a share intent
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("application/vnd.android.package-archive");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, tempUri);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Add read permission

                    // Show the share chooser
                    startActivity(Intent.createChooser(shareIntent, "Share APK via"));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Homepage2.this, "Error sharing APK", Toast.LENGTH_SHORT).show();
                }*/
            }
        });





        cvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Homepage2.this)
                        .setMessage("Do you want to log out and create a new account?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Log out
                                authProfile.signOut();
                                Toast.makeText(Homepage2.this, "Signed out", Toast.LENGTH_SHORT).show();

                                // Start SignUp activity
                                Intent intent = new Intent(Homepage2.this, Sign_up.class);
                                startActivity(intent);

                                // Clear the activity stack to prevent going back to previous activities
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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
                        Toast.makeText(Homepage2.this, "Sign out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Homepage2.this,Login.class);

                        //Clear stack to prevent userprofileactivity on pressing back button after loggging out
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
    }
