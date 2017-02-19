package com.example.balda.listtest.Models;

import java.util.List;
import java.util.Map;

/**
 * Created by balda on 17.02.2017.
 */

public class BeerListEntry {
    private String id;
    private String name;
    private long type;
    private String breweryID;
    private String photoFileID;
    private List<String> ratings;
    private List<String> userIDs;
    private List<String> dates;

    public BeerListEntry() {}

    public BeerListEntry(String name, long type, String breweryID)
    {
        this.type = type;
        this.name = name;
        this.breweryID = breweryID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getType() { return type; }

    public String getBreweryID() {
        return breweryID;
    }

    public String getPhotoFileID() {
        return photoFileID;
    }

    public void setPhotoFileID(String photoFileID) {
        this.photoFileID = photoFileID;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }

    public List<String> getRatings() {
        return ratings;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public  List<String> getDates() {
        return dates;
    }
}
