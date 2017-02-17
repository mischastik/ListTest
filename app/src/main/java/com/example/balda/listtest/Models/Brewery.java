package com.example.balda.listtest.Models;

/**
 * Created by balda on 17.02.2017.
 */

public class Brewery {
    private String id;
    private String name;
    private String location;
    private String logoFileID;


    public Brewery(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLogoFileID() {
        return logoFileID;
    }

    public void setLogoFileID(String logoFileID) {
        this.logoFileID = logoFileID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
