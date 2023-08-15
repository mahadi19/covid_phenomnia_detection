package com.facultyofengineeringcu12.covid_19_pneomonia;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.facultyofengineeringcu12.covid_19_pneomonia.Rating;

public class Activity_Rating2 extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button submitBtn;
    private TextView averageRatingText;
    private TextView userCountText;
    private TextView Yourrating;

     DatabaseReference ratingsRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating2);
        getSupportActionBar().setTitle("Rate our app");

        // Initialize Firebase database reference
        ratingsRef = FirebaseDatabase.getInstance().getReference("ratings");


        ratingBar = findViewById(R.id.ratingBar);
        submitBtn = findViewById(R.id.submitBtn);
        averageRatingText = findViewById(R.id.averageRatingText);
        userCountText = findViewById(R.id.userCountText);
        Yourrating = findViewById(R.id.YourRatingText);
        getSupportActionBar().setTitle("User reviews");


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                saveRating(rating);
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               Yourrating.setText(" Your rating :"+rating);

            }
        });

        calculateAverageRating();
        calculateUserCount();
    }

    private void saveRating(float rating) {
        DatabaseReference newRatingRef = ratingsRef.push();
        Rating ratingObj = new Rating(newRatingRef.getKey(), rating);

        newRatingRef.setValue(ratingObj)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Activity_Rating2.this, "Rating submitted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Rating2.this, "Failed to submit rating.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


//    private void calculateAverageRating() {
//        ratingsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                float totalRating = 0;
//                int numRatings = 0;
//
//                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
//                    Rating rating = ratingSnapshot.getValue(Rating.class);
//                    if (rating != null) {
//                        totalRating += rating.getRatingValue();
//                        numRatings++;
//                    }
//                }
//
//                if (numRatings > 0) {
//                    float averageRating = totalRating / numRatings;
//                    averageRatingText.setText(String.format("Average Rating: %.2f", averageRating));
//                } else {
//                    averageRatingText.setText("No ratings yet.");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(Activity_Rating2.this, "Failed to calculate average rating.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void calculateAverageRating() {
        ratingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float totalRating = 0;
                int numRatings = 0;

                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    Rating rating = ratingSnapshot.getValue(Rating.class);
                    if (rating != null && rating.getRatingValue() > 0) {
                        totalRating += rating.getRatingValue();
                        numRatings++;
                    }
                }

                if (numRatings > 0) {
                    float averageRating = totalRating / numRatings;
                    averageRatingText.setText(String.format("Average Rating: %.2f", averageRating));
                } else {
                    averageRatingText.setText("No ratings yet.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Activity_Rating2.this, "Failed to calculate average rating.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateUserCount() {
        ratingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int userCount = (int) dataSnapshot.getChildrenCount();
                userCountText.setText(String.format("Users Rated: %d", userCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Activity_Rating2.this, "Failed to calculate user count.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}