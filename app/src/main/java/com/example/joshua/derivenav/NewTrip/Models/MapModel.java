package com.example.joshua.derivenav.NewTrip.Models;

import java.util.ArrayList;

public class MapModel {

    private String title;

    private String message;


    public MapModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public MapModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
