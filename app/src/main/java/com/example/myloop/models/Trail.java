package com.example.myloop.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePolygon;
import com.parse.ParseUser;

@ParseClassName("Trail")
public class Trail extends ParseObject {
    public static final String ROUTE = "Route";
    public static final String TRAIL_NAME = "TrailName";
    public static final String TRAIL_DESCRIPTION = "Name";
    public static final String TRAIL_DISTANNCE = "Length";
    public static final String TRAIL_SCENERY = "Scenery";

    public ParseUser getTrail() {
        return getParseUser(TRAIL_NAME);
    }

    public void setTrail(ParseUser user) { put(TRAIL_NAME, user); }

    public String getTrailDescription() {
        return getString(TRAIL_DESCRIPTION);
    }

    public ParsePolygon getTrailRoute() { return getParsePolygon(ROUTE); }  //Array of GeoPoints

    public double getTrailDistance() { return getDouble(TRAIL_DISTANNCE); }

    public ParseFile getTrailScenery() { return getParseFile(TRAIL_SCENERY); }

}
