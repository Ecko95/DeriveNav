package com.example.joshua.derivenav.NewTrip.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joshua on 03/05/2018.
 */

public class PointOfInterestCity {


    @SerializedName("name")
    private String name = null;
    @SerializedName("geoname_id")
    private String geonameId = null;
    @SerializedName("location")
    private Geolocation location = null;
    @SerializedName("total_points_of_interest")
    private Integer totalPointsOfInterest = null;

    /**
     * The name of the location that was searched for
     **/

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The Geonames ID of the location that was searched for
     **/

    public String getGeonameId() {
        return geonameId;
    }
    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    /**
     **/

    public Geolocation getLocation() {
        return location;
    }
    public void setLocation(Geolocation location) {
        this.location = location;
    }

    /**
     * The total number of points_of_interest that YapQ has indexed for this city
     **/

    public Integer getTotalPointsOfInterest() {
        return totalPointsOfInterest;
    }
    public void setTotalPointsOfInterest(Integer totalPointsOfInterest) {
        this.totalPointsOfInterest = totalPointsOfInterest;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointOfInterestCity pointOfInterestCity = (PointOfInterestCity) o;
        return (this.name == null ? pointOfInterestCity.name == null : this.name.equals(pointOfInterestCity.name)) &&
                (this.geonameId == null ? pointOfInterestCity.geonameId == null : this.geonameId.equals(pointOfInterestCity.geonameId)) &&
                (this.location == null ? pointOfInterestCity.location == null : this.location.equals(pointOfInterestCity.location)) &&
                (this.totalPointsOfInterest == null ? pointOfInterestCity.totalPointsOfInterest == null : this.totalPointsOfInterest.equals(pointOfInterestCity.totalPointsOfInterest));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.name == null ? 0: this.name.hashCode());
        result = 31 * result + (this.geonameId == null ? 0: this.geonameId.hashCode());
        result = 31 * result + (this.location == null ? 0: this.location.hashCode());
        result = 31 * result + (this.totalPointsOfInterest == null ? 0: this.totalPointsOfInterest.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class PointOfInterestCity {\n");

        sb.append("  name: ").append(name).append("\n");
        sb.append("  geonameId: ").append(geonameId).append("\n");
        sb.append("  location: ").append(location).append("\n");
        sb.append("  totalPointsOfInterest: ").append(totalPointsOfInterest).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
