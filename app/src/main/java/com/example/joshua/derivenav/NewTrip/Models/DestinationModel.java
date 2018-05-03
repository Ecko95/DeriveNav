package com.example.joshua.derivenav.NewTrip.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class DestinationModel {

    @Expose
    private Integer id;
    @Expose
    private String title;
    @Expose
    private String message;
    @Expose
    private String thumbnailUrl;

    public DestinationModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public DestinationModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
