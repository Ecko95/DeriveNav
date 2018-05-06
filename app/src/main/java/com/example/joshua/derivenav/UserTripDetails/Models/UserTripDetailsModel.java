package com.example.joshua.derivenav.UserTripDetails.Models;

public class UserTripDetailsModel {

    private String key;
    private String cityName;
    private String userID;

    //same as Trip destinations a.k.a points of interest
    private String description;
    private String googleMaps;
    private String wikiPage;
    private String img;
    private double lat;
    private double lng;


    public UserTripDetailsModel(String cityName, String key, String userID) {
        this.cityName = cityName;
        this.key = key;
        this.userID = userID;
    }

    public UserTripDetailsModel() {

    }

    public UserTripDetailsModel(String cityName, String key, String userID, String description, String googleMaps, String wikiPage, String img, double lat, double lng) {
        this.key = key;
        this.cityName = cityName;
        this.userID = userID;
        this.description = description;
        this.googleMaps = googleMaps;
        this.wikiPage = wikiPage;
        this.img = img;
        this.lat = lat;
        this.lng = lng;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoogleMaps() {
        return googleMaps;
    }

    public void setGoogleMaps(String googleMaps) {
        this.googleMaps = googleMaps;
    }

    public String getWikiPage() {
        return wikiPage;
    }

    public void setWikiPage(String wikiPage) {
        this.wikiPage = wikiPage;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
