package com.example.joshua.derivenav.NewTrip.Models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by Joshua on 03/05/2018.
 */

public class Geolocation {
    @SerializedName("latitude")
    private BigDecimal latitude = null;
    @SerializedName("longitude")
    private BigDecimal longitude = null;
    @SerializedName("google_maps_link")
    private String googleMapsLink = null;

    /**
     * The north-south coordinate of this location, in decimal degrees, between -90 and 90
     **/

    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * The east-west coordinate of this location, in decimal degrees, between -180 and 180
     **/

    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * For YapQ APIs only  - a link to a google map rendering of this location.
     **/

    public String getGoogleMapsLink() {
        return googleMapsLink;
    }
    public void setGoogleMapsLink(String googleMapsLink) {
        this.googleMapsLink = googleMapsLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Geolocation geolocation = (Geolocation) o;
        return (this.latitude == null ? geolocation.latitude == null : this.latitude.equals(geolocation.latitude)) &&
                (this.longitude == null ? geolocation.longitude == null : this.longitude.equals(geolocation.longitude)) &&
                (this.googleMapsLink == null ? geolocation.googleMapsLink == null : this.googleMapsLink.equals(geolocation.googleMapsLink));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.latitude == null ? 0: this.latitude.hashCode());
        result = 31 * result + (this.longitude == null ? 0: this.longitude.hashCode());
        result = 31 * result + (this.googleMapsLink == null ? 0: this.googleMapsLink.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class Geolocation {\n");

        sb.append("  latitude: ").append(latitude).append("\n");
        sb.append("  longitude: ").append(longitude).append("\n");
        sb.append("  googleMapsLink: ").append(googleMapsLink).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
