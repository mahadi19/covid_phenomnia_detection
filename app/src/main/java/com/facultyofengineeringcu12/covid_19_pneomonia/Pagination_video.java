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

public class Pagination_video extends AppCompatActivity {
    private TextView tbm1, tb2, tbm3;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayerView youTubePlayerView2;
    private YouTubePlayerView youTubePlayerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination_video);

        tbm1 = findViewById(R.id.textview11);
        tbm3 = findViewById(R.id.textview31);

        youTubePlayerView2 = findViewById(R.id.youtube_player_view22);
        getLifecycle().addObserver(youTubePlayerView2);
        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "RTVaPiOKcQw";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        tbm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagination_video.this, Awareness.class);
                startActivity(intent);
            }
        });
        tbm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagination_video.this, Pagination_3.class);
                startActivity(intent);
            }
        });
    }
}