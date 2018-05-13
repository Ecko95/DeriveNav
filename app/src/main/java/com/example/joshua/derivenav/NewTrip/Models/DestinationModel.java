package com.example.joshua.derivenav.NewTrip.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DestinationModel {

    public DestinationModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public DestinationModel() {

    }

    //Test Model
    @Expose
    private String title;
    @Expose
    private String message;
    @Expose
    private String thumbnailUrl;

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

    //start of test user model
    @Expose
    @SerializedName("company")
    private Company company;
    @Expose
    @SerializedName("website")
    private String website;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("address")
    private Address address;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private int id;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Company {
        @Expose
        @SerializedName("bs")
        private String bs;
        @Expose
        @SerializedName("catchPhrase")
        private String catchPhrase;
        @Expose
        @SerializedName("name")
        private String name;

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Address {
        @Expose
        @SerializedName("geo")
        private Geo geo;
        @Expose
        @SerializedName("zipcode")
        private String zipcode;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("suite")
        private String suite;
        @Expose
        @SerializedName("street")
        private String street;

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }
    }

    public static class Geo {
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    //POINTS OF INTEREST MODEL

    @Expose
    @SerializedName("points_of_interest")
    private List<Points_of_interest> points_of_interest;
    @Expose
    @SerializedName("current_city")
    private Current_city current_city;

    public List<Points_of_interest> getPoints_of_interest() {
        return points_of_interest;
    }

    public void setPoints_of_interest(List<Points_of_interest> points_of_interest) {
        this.points_of_interest = points_of_interest;
    }

    public Current_city getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(Current_city current_city) {
        this.current_city = current_city;
    }

    public static class Points_of_interest {
        @Expose
        @SerializedName("location")
        private Location location;
        @Expose
        @SerializedName("details")
        private Details details;
        @Expose
        @SerializedName("main_image")
        private String main_image;
        @Expose
        @SerializedName("geoname_id")
        private int geoname_id;
        @Expose
        @SerializedName("grades")
        private Grades grades;
        @Expose
        @SerializedName("categories")
        private List<String> categories;
        @Expose
        @SerializedName("title")
        private String title;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
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

        public Grades getGrades() {
            return grades;
        }

        public void setGrades(Grades grades) {
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

    public static class Grades {
        @Expose
        @SerializedName("yapq_grade")
        private int yapq_grade;
        @Expose
        @SerializedName("city_grade")
        private int city_grade;

        public int getYapq_grade() {
            return yapq_grade;
        }

        public void setYapq_grade(int yapq_grade) {
            this.yapq_grade = yapq_grade;
        }

        public int getCity_grade() {
            return city_grade;
        }

        public void setCity_grade(int city_grade) {
            this.city_grade = city_grade;
        }
    }

    public static class Current_city {
        @Expose
        @SerializedName("total_points_of_interest")
        private int total_points_of_interest;
        @Expose
        @SerializedName("geoname_id")
        private int geoname_id;
        @Expose
        @SerializedName("location")
        private Location location;
        @Expose
        @SerializedName("name")
        private String name;

        public int getTotal_points_of_interest() {
            return total_points_of_interest;
        }

        public void setTotal_points_of_interest(int total_points_of_interest) {
            this.total_points_of_interest = total_points_of_interest;
        }

        public int getGeoname_id() {
            return geoname_id;
        }

        public void setGeoname_id(int geoname_id) {
            this.geoname_id = geoname_id;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CityLocation {
        @Expose
        @SerializedName("longitude")
        private double longitude;
        @Expose
        @SerializedName("latitude")
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

}
