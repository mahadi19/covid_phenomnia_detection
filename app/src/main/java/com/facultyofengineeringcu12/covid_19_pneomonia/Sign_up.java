package com.facultyofengineeringcu12.covid_19_pneomonia;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity {

    private TextView txtSignIn;
    private EditText fullname,password,repassword,mobile,email,date_of_birth;
    Button Register;
    private RadioGroup RadiogroupGender;
    private RadioButton GenderSelected;
    private ProgressBar progressBar;

    private DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Signup here");

        txtSignIn = findViewById(R.id.text_signin);

        fullname = findViewById(R.id.editText_register_full_name);
        password = findViewById(R.id.editText_register_password);
        repassword = findViewById(R.id.editText_register_repassword);
        mobile = findViewById(R.id.editText_register_mobile);
        email = findViewById(R.id.editText_register_email);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


        Register = findViewById(R.id.Button_Register);

        progressBar = findViewById(R.id.Progressbar);

        //date = findViewById(R.id.editText_register_date_of_birth_button);
        date_of_birth = findViewById(R.id.editText_register_date_of_birth);

        RadiogroupGender = findViewById(R.id.radio_group_register_id);
        RadiogroupGender.clearCheck();


        //Setting up date picker on edittext
        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day =calendar.get(Calendar.DAY_OF_MONTH);
                int month =calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);

                //date picker dialog
                picker = new DatePickerDialog(Sign_up.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_of_birth.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                },year,month,day);

                picker.show();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this,Login.class);
                startActivity(intent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectGenderId  = RadiogroupGender.getCheckedRadioButtonId();
                GenderSelected = findViewById(selectGenderId);

                // Obtain the entered data

                String textFullname = fullname.getText().toString();
                String textemail = email.getText().toString();

                String textDOB = date_of_birth.getText().toString();
                String textMobile = mobile.getText().toString();
                String textpassword = password.getText().toString();
                String textrepass = repassword.getText().toString();




                String textGender;//cant obtain te value before verifying

                //authenticate mobile no
                String mobileRegex ="^(?:\\+?88)?01[15-9]\\d{8}$";
                // String passwordRegex = "/^[a-zA-Z][0-9]$/";
                Matcher mobileMatcher ,passwordMatcher;
                Pattern mobilPattern = Pattern.compile(mobileRegex);
                //  Pattern passwordPattern = Pattern.compile(passwordRegex);
                mobileMatcher = mobilPattern.matcher(textMobile);
                // passwordMatcher = passwordPattern.matcher(textpassword);
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
                Pattern passwordPattern = Pattern.compile(passwordRegex);



                TextWatcher emailTextWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                            email.setError(null); // Clear any existing error
                        } else {
                            email.setError("Invalid email"); // Set an error message
                            email.requestFocus(); // Move focus to the email EditText
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                };

                // Attach the TextWatcher to the email EditText
                email.addTextChangedListener(emailTextWatcher);

                if (TextUtils.isEmpty(textFullname))
                {
                    Toast.makeText(Sign_up.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    fullname.setError("Full name is required.");
                    fullname.requestFocus();
                }

                else if (TextUtils.isEmpty(textemail) ){
                    Toast.makeText(Sign_up.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    email.setError("Email is required.");
                    email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches())
                {
                    Toast.makeText(Sign_up.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                    email.setError("Valid email required.");
                    email.requestFocus();
                }
                else if (TextUtils.isEmpty(textDOB))
                {
                    Toast.makeText(Sign_up.this, "Please enter your date of birth", Toast.LENGTH_LONG).show();
                    date_of_birth.setError("Date of birth is required.");
                    date_of_birth.requestFocus();
                }

                else if (RadiogroupGender.getCheckedRadioButtonId()== -1)
                {
                    Toast.makeText(Sign_up.this, "Please Select Gender", Toast.LENGTH_LONG).show();
                    GenderSelected.setError("Gender is required");
                    GenderSelected.requestFocus();
                }
                else if (TextUtils.isEmpty(textMobile))
                {
                    Toast.makeText(Sign_up.this, "Please enter your mobile no", Toast.LENGTH_LONG).show();
                    mobile.setError("Mobile no is required.");
                    mobile.requestFocus();
                }
                else if (!mobileMatcher.find())
                {
                    Toast.makeText(Sign_up.this, "Please enter your mobile no", Toast.LENGTH_LONG).show();
                    mobile.setError("Valid mobile no is required.");
                    mobile.requestFocus();
                }

                else if (TextUtils.isEmpty(textpassword)) {
                    Toast.makeText(Sign_up.this, "Please enter password", Toast.LENGTH_LONG).show();
                    password.setError("Password is required.");
                    password.requestFocus();
                } else if (!passwordPattern.matcher(textpassword).matches()) {
                    Toast.makeText(Sign_up.this, "Please enter a strong password", Toast.LENGTH_LONG).show();
                    password.setError("Password must be at least 6 characters long and include a mix of letters, digits, and special characters.");
                    password.requestFocus();
                }

                else if (textpassword.length() < 6) {
                    Toast.makeText(Sign_up.this, "Please enter strong password", Toast.LENGTH_LONG).show();
                    password.setError("Enter password more than six characters.");
                    password.requestFocus();
                }

                else if (TextUtils.isEmpty(textrepass)) {
                    Toast.makeText(Sign_up.this, "Please re-enter password", Toast.LENGTH_LONG).show();
                    repassword.setError("Confirm Password is required.");
                    repassword.requestFocus();
                } else if (!textpassword.equals(textrepass)) {
                    Toast.makeText(Sign_up.this, "Password do not match", Toast.LENGTH_LONG).show();
                    repassword.setError("Confirm password do not match.");
                    repassword.requestFocus();

                    // Clear the entered passwords
                    password.setText("");
                    repassword.setText("");
                }


                else
                {
                    textGender = GenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);

                    registerUser(textFullname,textemail,textDOB,textGender,textMobile,textpassword);


                }


            }

            private void registerUser(String textFullname, String textemail, String textDOB, String textGender, String textMobile, String textpassword) {


                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(textemail,textpassword).addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {


                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            //Update display name of user
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                            firebaseUser.updateProfile(profileChangeRequest);


                            //Enter userdata into the realtime database
                            ReadwriteUserDetails writeuserDetails = new ReadwriteUserDetails(textFullname,textDOB,textGender,textMobile,textemail);

                            //Extracting user reference from databse for "Registered user"

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeuserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        //Send verification email
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(Sign_up.this, "Registration successfull.Please verify your email", Toast.LENGTH_LONG).show();
                                        // Open user profile after succesfull registration


                                        Intent intent = new Intent(Sign_up.this,Login.class);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();  //to close register activity*/


                                    }

                                    else
                                    {
                                        Toast.makeText(Sign_up.this, "Registration failed.Try again", Toast.LENGTH_LONG).show();

                                    }

                                    progressBar.setVisibility(View.GONE);
                                }

                            });

                        }
                        else
                        {
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e) {
                                password.setError("Your password is too weak kindly use mix of alphabet,number and special symbol");
                                password.requestFocus();
                            } catch(FirebaseAuthInvalidCredentialsException e)
                            {
                                password.setError("User already registered with this email try with another one");
                                password.requestFocus();
                            } catch(FirebaseAuthUserCollisionException e)
                            {
                                password.setError("User already registered with this email try with another one");
                                password.requestFocus();
                            }
                            catch(Exception e)
                            {
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(Sign_up.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });





    }
}