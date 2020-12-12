package com.example.myloop.models;


import com.google.gson.annotations.SerializedName;

public class Segment {

    @SerializedName("id")
    private long segment_id;

    private float distance;

    @SerializedName("name")
    private String segment_name;

    private String points;
    private String activity_type;


    public long getSegment_id() {
        return segment_id;
    }

    public float getDistance() {
        return distance;
    }

    public String getSegment_name() {
        return segment_name;
    }

    public String getPoints() {
        return points;
    }

    public String getActivity_type() {
        return activity_type;
    }

}
