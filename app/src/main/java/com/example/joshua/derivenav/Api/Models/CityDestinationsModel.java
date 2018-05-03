package com.example.joshua.derivenav.Api.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Joshua on 03/05/2018.
 */

public class CityDestinationsModel implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String title;
    @Expose
    private String thumbnailUrl;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
