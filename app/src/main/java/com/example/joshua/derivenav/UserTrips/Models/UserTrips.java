package com.example.joshua.derivenav.UserTrips.Models;

import android.net.Uri;

import java.util.ArrayList;

public class UserTrips {

    private String title;

    private String description;

    private String key;

    private String img;


    public UserTrips() {

    }

    public UserTrips(String title, String description, String key, String img) {
        this.title = title;
        this.description = description;
        this.key = key;
        this.img = img;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
