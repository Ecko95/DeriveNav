package com.example.joshua.derivenav.com.joshua.api.model;

/**
 * Created by Joshua on 25/04/2018.
 */

public class City {

    private String name;
    private String desc;

    public City(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}