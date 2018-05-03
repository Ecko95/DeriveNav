package com.example.joshua.derivenav.com.joshua.api.model;

import android.content.Intent;

/**
 * Created by Joshua on 25/04/2018.
 */

public class City {

//    private String name;
    private String desc;

    private String name;
    private Integer geonameId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

//
    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
