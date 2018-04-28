package com.example.joshua.derivenav.UserTrips;

import java.util.ArrayList;

public class UserTrips {

    private String title;

    private String description;

    private String key;


    public UserTrips(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public UserTrips() {

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
