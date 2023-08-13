package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

public class UploadProfilePictureActivity extends AppCompatActivity {

    private ImageView imageViewUploadProfilepic;
    private FirebaseAuth authProfile;
    StorageReference storageReference;
    private FirebaseUser firebaseUser;
    //   Uri filepath;
    Bitmap bitmap;
    Button buttonUploadpicChoose,buttonUpload;
    private static final int PICK_IMAGE_REQUEST =1;
    Uri uriImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_picture);


    }
}