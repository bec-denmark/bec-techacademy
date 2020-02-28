package com.bec.techacademy.learnkafka.datamodel.event;


import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AccountPostingsEvent implements ChangeDataEvent {

    private String postingType;
    private String postDate;
    private String postDescription;
    private Double postAmount = 0.0;
    private String postCurrency ="DKK";
    
    public AccountPostingsEvent(String postType, String posting, double postAmount, String postCurrency)  {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        postDate = sdf.format(date);

        postingType = postType;
        postDescription = posting;
        this.postAmount = postAmount;
        this.postCurrency = postCurrency;

    }

    @Override
    public String toString() {
        JsonObject obj = new JsonObject();
        obj.addProperty("postType",postingType);
        obj.addProperty("postDate",postDate);
        obj.addProperty("postDescription",postDescription);
        obj.addProperty("postAmount",postAmount);
        obj.addProperty("postCurrency",postCurrency);
        return obj.toString();
    }



    public AccountPostingsEvent setPostingType(String postingType) {
        this.postingType = postingType;
        return this;
    }

    public AccountPostingsEvent setPostDate(String postDate) {
        this.postDate = postDate;
        return this;
    }

    public AccountPostingsEvent setPostDescription(String postDescription) {
        this.postDescription = postDescription;
        return this;
    }

    public AccountPostingsEvent setPostAmount(Double postAmount) {
        this.postAmount = postAmount;
        return this;
    }

    public AccountPostingsEvent setPostCurrency(String postCurrency) {
        this.postCurrency = postCurrency;
        return this;
    }

}
