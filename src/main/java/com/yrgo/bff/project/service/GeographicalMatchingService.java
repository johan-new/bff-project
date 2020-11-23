package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.GpsCoordinates;

public class GeographicalMatchingService {
    //reach - range in kilometers

    /**
     *
     * GeographicalMatchingService
     *
     * Definitions
     * Latitude: 1 deg = 110.574 km
     * Longitude: 1 deg = 111.320*cos(latitude) km
     *
     *
     * @author Johan Nyberg
    * */

    public boolean match(GpsCoordinates coordinatesPlayerA, double rangePlayerA,
                         GpsCoordinates coordinatesPlayerB, double rangePlayerB){

        double  aLat, aMinLat,aMaxLat, aLong, aMinLong, aMaxLong,
                bLat, bMinLat, bMaxLat, bLong, bMinLong, bMaxLong;

        aLat = coordinatesPlayerA.getLatitude();

        //TODO: Implement

    }

    /**
     * @param kilometers - kilometers you would like to convert to degrees
     * @return corresponding amount of degrees (Longitude)
     * */
    private double kilometersToDegreesLongitude(double kilometers){
        return 1/(1.11320*Math.cos(Math.toRadians(kilometers)));
    }

    /**
     * @param kilometers - kilometers you would like to convert to degrees
     * @return corresponding amount of degrees (Latitude)
     * */
    private double kilometersToDegreesLatitude(double kilometers){
        return 1/(kilometers*1.10574);
    }

    /**
     * Check if a value i within an interval
     *
     * */
    private boolean isWithinInterval(double valueToCheck, double minvalue, double maxvalue){
        return valueToCheck >= minvalue && valueToCheck <= maxvalue;
    }
}
