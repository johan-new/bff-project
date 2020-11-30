package com.yrgo.bff.project.domain;


public class GpsCoordinates {

    private double longitude, latitude;

    public GpsCoordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }



}
