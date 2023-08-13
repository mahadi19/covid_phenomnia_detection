package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    TextView pwelcome,pfullname,pemail,pbirthday,pmobile,pgender;
    ProgressBar progressBar;
    String fullname,email,dob,gender,mobile;
    ImageView imageview;
    FirebaseAuth authProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("User profile");

        pwelcome = findViewById(R.id.textView_showwelcome);
        pfullname = findViewById(R.id.textView_show_fullname);
        imageview = findViewById(R.id.imageView_profile_photo);
        imageview.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this,UserProfilePicUploadActivity.class);
            startActivity(intent);
        });
        pemail = findViewById(R.id.textView_show_email);
        pbirthday = findViewById(R.id.textView_show_birthdate);
        pmobile = findViewById(R.id.textView_mobile_no);
        pgender = findViewById(R.id.textView_show_gender);
        progressBar = findViewById(R.id.ProgressbarID);


        authProfile = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser == null)
        {
            Toast.makeText(UserProfileActivity.this, "Something went wrong. Users details not available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ceckifEmailVarified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }



    }

    //User coming to userprofile afteer succesfull registration

    private void ceckifEmailVarified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified())
        {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        //set up alert builder

        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Email not varified");
        builder.setMessage("Please verify your email now. You can not continue without email varification.");

        //Open email app
        builder.setPositiveButton("Continue", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //to open in our email app not in this
            startActivity(intent);
        });
        //Create  alert dialog
        AlertDialog alertDialog = builder.create();

        //show alert dialog
        alertDialog.show();
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();


        //Extracting user references from databse for registered users only
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadwriteUserDetails readUserDetails = snapshot.getValue(ReadwriteUserDetails.class);
                if (readUserDetails != null)
                {
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    dob = readUserDetails.dob;
                    gender = readUserDetails.gender;
                    mobile = readUserDetails.mobile;

                    pwelcome.setText("Welcome  "+fullname+" !");
                    pfullname.setText(fullname);
                    pemail.setText(email);
                    pbirthday.setText(dob);
                    pgender.setText(gender);
                    pmobile.setText(mobile);


                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

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
            Intent intent = new Intent(UserProfileActivity.this,ProfileUpdate.class);
            startActivity(intent);
        }

        else if(id == R.id.menu_update_email)
        {
            Intent intent = new Intent(UserProfileActivity.this,Homepage2.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_settings)
        {
            Toast.makeText(this, "Menu setting", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.menu_change_password)
        {
            Intent intent = new Intent(UserProfileActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
        }
        else if(id ==R.id.menu_delete_profile)
        {
            Intent intent = new Intent(UserProfileActivity.this,Rating_again.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_change_password)
        {
            Intent intent = new Intent(UserProfileActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.menu_logout)
        {
            authProfile.signOut();
            Toast.makeText(UserProfileActivity.this, "Sign out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserProfileActivity.this,Login.class);

            //Clear stack to prevent userprofileactivity on pressing back button after loggging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}