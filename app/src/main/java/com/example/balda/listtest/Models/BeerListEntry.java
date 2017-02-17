package com.example.balda.listtest.Models;

import java.util.Map;

/**
 * Created by balda on 17.02.2017.
 */

public class BeerListEntry {
    private String id;
    private String name;
    private String type;
    private Brewery brewery;
    private String photoFileID;
    //private Map<String, String> tests;

    public BeerListEntry(String name, String type, Brewery brewery)
    {
        this.type = type;
        this.name = name;
        this.brewery = brewery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() { return type; }

    //public Map<String, Boolean> getTests() {
    //    return tests;
    //}

    public Brewery getBrewery() {
        return brewery;
    }

    public String getPhotoFileID() {
        return photoFileID;
    }

    public void setPhotoFileID(String photoFileID) {
        this.photoFileID = photoFileID;
    }
}
