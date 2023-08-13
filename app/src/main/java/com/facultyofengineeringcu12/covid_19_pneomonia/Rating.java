package com.facultyofengineeringcu12.covid_19_pneomonia;

public class Rating extends Activity_Rating2{

    private String ratingId;
    private float ratingValue;

    public Rating() {
        // Default constructor required for Firebase
    }

    public Rating(String ratingId, float ratingValue) {
        this.ratingId = ratingId;
        this.ratingValue = ratingValue;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }
}
