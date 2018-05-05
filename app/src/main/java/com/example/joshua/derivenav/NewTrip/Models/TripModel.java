package com.example.joshua.derivenav.NewTrip.Models;

import android.support.annotation.Nullable;

/**
 * Created by Joshua on 04/05/2018.
 */

public class TripModel {


    private String name;
    private String desc;
    @Nullable
    private String pushID;

    public TripModel(String name, String desc, String pushID) {
        this.name = name;
        this.desc = desc;
        this.pushID = pushID;
    }

    public TripModel() {

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
