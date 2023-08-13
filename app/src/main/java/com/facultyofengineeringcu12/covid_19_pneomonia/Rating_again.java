package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rating_again extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView ratingText;
    private TextView ratingCount;

    private FirebaseDatabase firebaseDatabase;
    private float totalRating = 0;
    private int numberOfRatings = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_again);

        // Initialize Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();

        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.ratingText);
        ratingCount = findViewById(R.id.ratingCount);

        // Set RatingBar change listener
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                // Save rating to Firebase Database
                saveRatingToDatabase(rating);

                // Update UI
                totalRating += rating;
                numberOfRatings++;
                updateRatingUI();
            }
        });

        // Retrieve and display average rating and rating count
        retrieveAndDisplayRating();
    }

    private void saveRatingToDatabase(float rating) {
        String userId = "replace_with_user_id"; // You can replace this with the actual user ID
        firebaseDatabase.getReference("ratings")
                .child("users")
                .child(userId)
                .push()
                .setValue(rating);
    }

    private void updateRatingUI() {
        float averageRating = totalRating / numberOfRatings;
        ratingText.setText("Average Rating: " + averageRating);
        ratingCount.setText("Number of Ratings: " + numberOfRatings);
    }

    private void retrieveAndDisplayRating() {
        String userId = "replace_with_user_id"; // You can replace this with the actual user ID

        firebaseDatabase.getReference("ratings")
                .child("users")
                .child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            totalRating = 0;
                            numberOfRatings = 0;

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                float rating = snapshot.getValue(Float.class);
                                totalRating += rating;
                                numberOfRatings++;
                            }

                            updateRatingUI();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }
}