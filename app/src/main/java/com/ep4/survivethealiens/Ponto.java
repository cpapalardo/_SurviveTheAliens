package com.ep4.survivethealiens;

/**
 * Created by Carla on 20/10/2016.
 */

public class Ponto {
    private double latitude;
    private double longitude;

    public Ponto(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLongitude(){
        return longitude;
    }
}
