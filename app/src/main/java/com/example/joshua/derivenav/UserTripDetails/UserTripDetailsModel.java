package com.example.joshua.derivenav.UserTripDetails;

import java.util.ArrayList;
import java.util.Map;

public class UserTripDetailsModel {

    private String title;

    private String message;

    private String pushID;

    private String city_name;

    public UserTripDetailsModel(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public UserTripDetailsModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public UserTripDetailsModel(String name, String desc, String pushID) {
    }

    public UserTripDetailsModel() {

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
