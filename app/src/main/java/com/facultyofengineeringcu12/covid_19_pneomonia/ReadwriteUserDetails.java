package com.facultyofengineeringcu12.covid_19_pneomonia;

public class ReadwriteUserDetails {

    public String dob,gender,mobile,email,fullname;
    //Constructor
    public ReadwriteUserDetails(){};

    public ReadwriteUserDetails(String textFullname, String textDOB, String textGender, String textMobile, String textemail)
    {

        this.dob = textDOB;
        this.gender = textGender;
        this.mobile = textMobile;
        this.email=textemail;
        this.fullname =textFullname;
    }
}
