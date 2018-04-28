package com.example.joshua.derivenav.com.joshua.api.model;

import android.support.annotation.Nullable;

/**
 * Created by Joshua on 25/04/2018.
 */

public class Trip {

    private String name;
    private String desc;
    @Nullable private String pushID;

    public Trip(String name, String desc,String pushID) {
        this.name = name;
        this.desc = desc;
        this.pushID = pushID;
    }

    public Trip() {

    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
