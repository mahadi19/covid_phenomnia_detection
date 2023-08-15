package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button button_reset2;
    private EditText registeredEmail2;
    private ProgressBar progressBar;

    private FirebaseAuth authProfile;

    private final static String TAG = "ForgotPasswordActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        button_reset2 = findViewById(R.id.Reset_password_button_2);
        registeredEmail2 = findViewById(R.id.registeredEmail);
        progressBar = findViewById(R.id.Progressbar2);

        getSupportActionBar().setTitle("Recover your password");

        button_reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registeredEmail2.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    registeredEmail2.setError("Email is required.");
                    registeredEmail2.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                    registeredEmail2.setError("Valid email required.");
                    registeredEmail2.requestFocus();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }

            }
        });



    }

    private void resetPassword(String email) {
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please check your gmail inbox to reset the password", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgotPasswordActivity.this,Login.class);

                    //Clear stack to prevent user coming back to forgot activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    try{
                        throw task.getException();
                    } catch(FirebaseAuthInvalidUserException e)
                    {
                        registeredEmail2.setError("User does not exists or no longer available ");
                    } catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}