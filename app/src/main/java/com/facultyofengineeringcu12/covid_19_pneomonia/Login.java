package com.facultyofengineeringcu12.covid_19_pneomonia;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Login extends AppCompatActivity {
    private EditText editTextemail, editTextPassword;
    private TextView textView_register_login_page, forgot_password;
    private Button button_login_page_login;

    ImageView showhide;

    ProgressBar progressBar;
    FirebaseAuth authProfile;

    // SharedPreferences constants
    private static final String PREFS_NAME = "UserPrefs";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextemail = findViewById(R.id.editText_loginpage_email);
        editTextPassword = findViewById(R.id.editText_loginpage_password);
        button_login_page_login = findViewById(R.id.loginpage_button);
        textView_register_login_page = findViewById(R.id.textView_Register_login_page);

        CheckBox rememberMeCheckbox = findViewById(R.id.remember_me_checkbox);

        forgot_password = findViewById(R.id.forgot_password_textview_button);

        showhide = findViewById(R.id.editText_loginpage_eye);

        showhide.setImageResource(R.drawable.ic_hide_pwd);

        progressBar = findViewById(R.id.Progressbar1);

        authProfile = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        retrieveUserCredentials();

        rememberMeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Save user credentials if the checkbox is checked
                    saveUserCredentials(editTextemail.getText().toString(), editTextPassword.getText().toString());
                }
            }
        });


        // TextWatcher to check if an account exists with the entered email while typing
        editTextemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Check if the entered email is associated with an existing user
                String email = charSequence.toString();
                if (!TextUtils.isEmpty(email)) {
                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        SignInMethodQueryResult result = task.getResult();
                                        if (result != null && result.getSignInMethods() != null &&
                                                result.getSignInMethods().size() > 0) {
                                            // Account exists
                                            editTextemail.setError(null); // Clear any previous error
                                        } else {
                                            // Account doesn't exist
                                            editTextemail.setError("Account does not exist");
                                        }
                                    }
                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPasswordActivity.class));
                Toast.makeText(Login.this, "Reset your password", Toast.LENGTH_SHORT).show();
            }
        });

        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //password is visible so hide it
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    showhide.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showhide.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        textView_register_login_page.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Sign_up.class);
            startActivity(intent);
        });

        button_login_page_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextemail.getText().toString();
                String textPassword = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextemail.setError("Email is required.");
                    editTextemail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(Login.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                    editTextemail.setError("Valid email required.");
                    editTextemail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextPassword.setError("Password is required.");
                    editTextPassword.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPassword);
                }
            }
        });
    }

    private void loginUser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //get instance of the current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //check if email is verified before access their profile
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        //open user profile
                        startActivity(new Intent(Login.this, Homepage.class));
                        finish();
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        editTextemail.setError("User doesn't exist or no longer available. Register again");
                        editTextemail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextemail.setError("Invalid password or email");
                        editTextemail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        // set up alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You cannot verify without email verification.");

        // Open email app
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // Create alert dialog
        AlertDialog alertDialog = builder.create();

        // Show alert dialog
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this, Homepage.class));
            finish();
        } else {
            Toast.makeText(this, "Log in here", Toast.LENGTH_SHORT).show();
        }
    }

    // Additional methods for saving and retrieving user credentials

    private void saveUserCredentials(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_PASSWORD, password);
        editor.apply();
    }

    private void retrieveUserCredentials() {
        String savedEmail = sharedPreferences.getString(PREF_EMAIL, "");
        String savedPassword = sharedPreferences.getString(PREF_PASSWORD, "");

        editTextemail.setText(savedEmail);
        editTextPassword.setText(savedPassword);
    }

    // Your existing ClickListeners and other methods


}
