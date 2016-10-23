package com.ep4.survivethealiens.Model;

/**
 * Created by Carla on 20/10/2016.
 */

public class Ponto {
    private double Latitude;
    private double Longitude;

    public Ponto(double latitude, double longitude){
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.Latitude = latitude;
    }

    public double getLatitude(){
        return Latitude;
    }

    public void setLongitude(double longitude){
        this.Longitude = longitude;
    }

    public double getLongitude(){
        return Longitude;
    }
}
