package com.example.myloop;

import com.example.myloop.models.Segment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StravaApi {

    /* Retrofit will return a list of JsonObj of Segments */
    @GET("segment/explore")
    Call<List<Segment>> getExploreSegment(
            @Query("bounds") Float bounds[],
            @Query("activity_type")  String activityType,
            @Query("min_cat") Integer minClimbingCtg,
            @Query("max_cat") Integer maxClimbingCtg);
}
