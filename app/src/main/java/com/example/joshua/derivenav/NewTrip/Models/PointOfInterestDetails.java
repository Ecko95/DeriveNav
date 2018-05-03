package com.example.joshua.derivenav.NewTrip.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joshua on 03/05/2018.
 */

public class PointOfInterestDetails {

    @SerializedName("short_description")
    private String shortDescription = null;
    @SerializedName("description")
    private String description = null;
    @SerializedName("wiki_page_link")
    private String wikiPageLink = null;

    /**
     * A summary of the given point
     **/

    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * A paragraph describing this point of interest
     **/

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A link to this point of interest&quot;s wikipedia page
     **/

    public String getWikiPageLink() {
        return wikiPageLink;
    }
    public void setWikiPageLink(String wikiPageLink) {
        this.wikiPageLink = wikiPageLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointOfInterestDetails pointOfInterestDetails = (PointOfInterestDetails) o;
        return (this.shortDescription == null ? pointOfInterestDetails.shortDescription == null : this.shortDescription.equals(pointOfInterestDetails.shortDescription)) &&
                (this.description == null ? pointOfInterestDetails.description == null : this.description.equals(pointOfInterestDetails.description)) &&
                (this.wikiPageLink == null ? pointOfInterestDetails.wikiPageLink == null : this.wikiPageLink.equals(pointOfInterestDetails.wikiPageLink));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.shortDescription == null ? 0: this.shortDescription.hashCode());
        result = 31 * result + (this.description == null ? 0: this.description.hashCode());
        result = 31 * result + (this.wikiPageLink == null ? 0: this.wikiPageLink.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class PointOfInterestDetails {\n");

        sb.append("  shortDescription: ").append(shortDescription).append("\n");
        sb.append("  description: ").append(description).append("\n");
        sb.append("  wikiPageLink: ").append(wikiPageLink).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
