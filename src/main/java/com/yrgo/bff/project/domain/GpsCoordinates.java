package com.yrgo.bff.project.domain;


public class GpsCoordinates {

    private long longitude, latitude;

    public GpsCoordinates(long longitude, long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GpsCoordinates(double v, double v1) {
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }



}
