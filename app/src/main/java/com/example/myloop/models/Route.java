package com.example.myloop.models;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    private Drawable image;
    private float distance;
    private List<LatLng> route;
    private String name;

    public Route(Drawable image, float distance, List<LatLng> route, String name) {
        this.image = image;
        this.distance = distance;
        this.route = route;
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public List<LatLng> getRoute() {
        return route;
    }

    public void setRoute(List<LatLng> route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
