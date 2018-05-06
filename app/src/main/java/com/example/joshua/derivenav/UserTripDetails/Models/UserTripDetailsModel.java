package com.example.joshua.derivenav.UserTripDetails.Models;

public class UserTripDetailsModel {

    private String key;
    private String cityName;
    private String userID;

    public UserTripDetailsModel(String key, String cityName, String userID) {
        this.key = key;
        this.cityName = cityName;
        this.userID = userID;
    }

    public UserTripDetailsModel() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
