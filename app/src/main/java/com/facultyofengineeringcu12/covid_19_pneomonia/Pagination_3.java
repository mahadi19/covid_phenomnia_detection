package com.facultyofengineeringcu12.covid_19_pneomonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Pagination_3 extends AppCompatActivity {

    private TextView tb1, tb2,tb3;
    YouTubePlayerView youTubePlayerView ,youTubePlayerView2,youTubePlayerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination3);

        tb2 = findViewById(R.id.textview2);
        tb1 = findViewById(R.id.textview1);


        youTubePlayerView3 = findViewById(R.id.youtube_player_view4);
        getLifecycle().addObserver(youTubePlayerView3);

        youTubePlayerView3.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "3iUlh1qct60";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        tb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Pagination_3.this,Awareness.class);
                startActivity(intent);
            }
        });
        tb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Pagination_3.this,Pagination_video.class);
                startActivity(intent);
            }
        });


    }
}