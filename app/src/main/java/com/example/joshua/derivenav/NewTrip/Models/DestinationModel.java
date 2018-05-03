package com.example.joshua.derivenav.NewTrip.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @Expose
    private String name;



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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
