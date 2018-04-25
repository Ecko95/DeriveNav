package com.example.joshua.derivenav.com.joshua.api.model;

import android.net.Uri;

/**
 * Created by Joshua on 24/04/2018.
 */

public class User {
    private String name;
    private String email;
    private Uri photoUrl;
    //Trips setters and getters

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }
}

