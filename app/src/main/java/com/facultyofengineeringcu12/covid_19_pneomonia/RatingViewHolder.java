package com.facultyofengineeringcu12.covid_19_pneomonia;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class RatingViewHolder extends RecyclerView.ViewHolder{

    ImageView ratingUserImage;
    TextView ratingUserName,ratingText, ratingCnt;

    public RatingViewHolder(@NonNull View itemView) {
        super(itemView);

        ratingUserImage = itemView.findViewById(R.id.ratingUserImage);
        ratingUserName = itemView.findViewById(R.id.ratingUserName);
        ratingText = itemView.findViewById(R.id.userFeedback);
        ratingCnt = itemView.findViewById(R.id.userOwnRating);

    }

}
