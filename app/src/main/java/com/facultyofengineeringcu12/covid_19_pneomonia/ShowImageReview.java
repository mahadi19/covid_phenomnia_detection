package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowImageReview extends AppCompatActivity {

    ImageView Img1,img2,img3,img4,img5,img6;

    private RecyclerView recyclerView;
    private List<Upload> uploadList;
    DatabaseReference databaseReference, likeref;
    private ProgressBar progressBar;
    Boolean testclick = false;

    FirebaseAuth authProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_review);
        getSupportActionBar().setTitle("Recently posted");




        Img1 = findViewById(R.id.GO_to_homepage);
        img2 = findViewById(R.id.Post_to_timeline);
        img3 = findViewById(R.id.Visit_profile_info);
        img4 = findViewById(R.id.Set_user_name_Photo);
        img5 = findViewById(R.id.Detect_disease);
        img6 = findViewById(R.id.LogoutID);

        authProfile = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.recycleProgressId);


        uploadList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        likeref =  FirebaseDatabase.getInstance().getReference("Likes");

        Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowImageReview.this,Homepage.class);
                        startActivity(intent);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowImageReview.this,ImageReview.class);
                startActivity(intent);

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowImageReview.this,UserProfileActivity.class);
                startActivity(intent);

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowImageReview.this,ProfileUpdate.class);
                startActivity(intent);

            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowImageReview.this,MainActivity.class);
                startActivity(intent);

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onLogoutPressed();


            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnap : snapshot.getChildren()){
                    Upload upload = datasnap.getValue(Upload.class);
                    uploadList.add(upload);
                }

                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ShowImageReview.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });



        FirebaseRecyclerOptions<Upload> options =
                new FirebaseRecyclerOptions.Builder<Upload>()
                        .setQuery(databaseReference, Upload.class)
                        .build();


        FirebaseRecyclerAdapter<Upload, MyViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Upload model) {
                Upload upload = uploadList.get(position);
                holder.textView.setText(upload.getImageName());
                holder.userTextView.setText(upload.getUsername());
                Picasso.get().load(upload.getImageUri())
                        .fit()
                        .centerCrop()
                        .into(holder.imageView);

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = firebaseUser.getUid();
                String postkey = getRef(position).getKey();

                holder.getLikeStatus(postkey,userId);


                holder.likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        testclick = true;

                        likeref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(testclick == true){
                                    if(snapshot.child(postkey).hasChild(userId)){
                                        likeref.child(postkey).child(userId).removeValue();
                                        testclick = false;
                                    }
                                    else{
                                        likeref.child(postkey).child(userId).setValue(true);
                                        testclick = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),CommentPanel.class);
                        intent.putExtra("postkey",postkey);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample,parent,false);
                return new MyViewHolder(view);

            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void onLogoutPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Confirm Logout !")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                                        authProfile.signOut();
                Toast.makeText(ShowImageReview.this, "Sign out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShowImageReview.this,Login.class);

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