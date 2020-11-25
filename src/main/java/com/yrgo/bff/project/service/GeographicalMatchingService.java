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

    public boolean match(GpsCoordinates playerA, GpsCoordinates playerB){
        final double range = 0.1; // +- 0.1 corresponds to approx 10 km radius in Sweden

        return (isWithinInterval(playerA.getLongitude(),playerB.getLongitude()-range, playerB.getLongitude()+range) &&
                isWithinInterval(playerA.getLatitude(), playerB.getLatitude()-range, playerB.getLatitude()+range) &&
                isWithinInterval(playerB.getLatitude(),playerA.getLatitude()-range,playerA.getLatitude()+range) &&
                isWithinInterval(playerB.getLongitude(),playerA.getLongitude()-range,playerB.getLongitude()+range));

    }

    public boolean matchApprox(GpsCoordinates coordinatesPlayerA, double rangePlayerA,
                         GpsCoordinates coordinatesPlayerB, double rangePlayerB){

        double  aLat, aMinLat,aMaxLat, aLong, aMinLong, aMaxLong,
                bLat, bMinLat, bMaxLat, bLong, bMinLong, bMaxLong;

        //convert from km to degrees

        aLat = coordinatesPlayerA.getLatitude();
        aMinLat = aLat-kilometersToDegreesLatitude(rangePlayerA);
        aMaxLat = aLat+kilometersToDegreesLatitude(rangePlayerA);
        aLong = coordinatesPlayerA.getLongitude();
        aMinLong = aLong-kilometersToDegreesLongitude(rangePlayerA);
        aMaxLong = aLong+kilometersToDegreesLongitude(rangePlayerA);

        bLat = coordinatesPlayerB.getLatitude();
        bMinLat = bLat-kilometersToDegreesLatitude(rangePlayerB);
        bMaxLat = bLat+kilometersToDegreesLatitude(rangePlayerB);
        bLong = coordinatesPlayerB.getLongitude();
        bMinLong = bLong-kilometersToDegreesLongitude(rangePlayerB);
        bMaxLong = bLong+kilometersToDegreesLongitude(rangePlayerB);

        if (isWithinInterval(aLat,bMinLat,bMaxLat) &&
                isWithinInterval(aLong,bMinLong,bMaxLong) &&
                isWithinInterval(bLat,aMinLat,aMaxLat) &&
                isWithinInterval(bLong,aMinLong,aMaxLong))
        {
            return true;
        } else {
            return false;
        }


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
