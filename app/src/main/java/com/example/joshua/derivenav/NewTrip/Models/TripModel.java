package com.example.joshua.derivenav.NewTrip.Models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joshua on 04/05/2018.
 */

public class TripModel {


    private String name;
    private String desc;
    private String pushID;
    private String tripImg;

    public TripModel(String name, String desc, String pushID, String tripImg) {
        this.name = name;
        this.desc = desc;
        this.pushID = pushID;
        this.tripImg = tripImg;
    }

    public TripModel(String name, String desc, String pushID) {
        this.name = name;
        this.desc = desc;
        this.pushID = pushID;
    }


    public TripModel() {
    }

    public String getTripImg() {
        return tripImg;
    }

    public void setTripImg(String tripImg) {
        this.tripImg = tripImg;
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


    private List<DestinationModel.Points_of_interest> points_of_interest;

    public List<DestinationModel.Points_of_interest> getPoints_of_interest() {
        return points_of_interest;
    }

    public static class Points_of_interest {
        @Expose
        @SerializedName("location")
        private DestinationModel.Location location;
        @Expose
        @SerializedName("details")
        private DestinationModel.Details details;
        @Expose
        @SerializedName("main_image")
        private String main_image;
        @Expose
        @SerializedName("geoname_id")
        private int geoname_id;
        @Expose
        @SerializedName("grades")
        private DestinationModel.Grades grades;
        @Expose
        @SerializedName("categories")
        private List<String> categories;
        @Expose
        @SerializedName("title")
        private String title;

        public DestinationModel.Location getLocation() {
            return location;
        }

        public void setLocation(DestinationModel.Location location) {
            this.location = location;
        }

        public DestinationModel.Details getDetails() {
            return details;
        }

        public void setDetails(DestinationModel.Details details) {
            this.details = details;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }

        public int getGeoname_id() {
            return geoname_id;
        }

        public void setGeoname_id(int geoname_id) {
            this.geoname_id = geoname_id;
        }

        public DestinationModel.Grades getGrades() {
            return grades;
        }

        public void setGrades(DestinationModel.Grades grades) {
            this.grades = grades;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Location {
        @Expose
        @SerializedName("google_maps_link")
        private String google_maps_link;
        @Expose
        @SerializedName("latitude")
        private double latitude;
        @Expose
        @SerializedName("longitude")
        private double longitude;

        public String getGoogle_maps_link() {
            return google_maps_link;
        }

        public void setGoogle_maps_link(String google_maps_link) {
            this.google_maps_link = google_maps_link;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Details {
        @Expose
        @SerializedName("short_description")
        private String short_description;
        @Expose
        @SerializedName("wiki_page_link")
        private String wiki_page_link;
        @Expose
        @SerializedName("description")
        private String description;

        public String getShort_description() {
            return short_description;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }

        public String getWiki_page_link() {
            return wiki_page_link;
        }

        public void setWiki_page_link(String wiki_page_link) {
            this.wiki_page_link = wiki_page_link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }





}
