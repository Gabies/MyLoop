package com.example.myloop.helpers;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.myloop.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.myloop.constants.Constants.MID_POINT;
import static com.example.myloop.constants.Constants.START_POINT;
import static com.example.myloop.constants.Constants.STOP_POINT;

public class MapService implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, TaskLoadedCallback {
    private GoogleMap mMap;
    private MapView mapFragment;
    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Geocoder geoCoder;

    private LocationManager locationManager;
    private boolean GpsStatus;
    private Polyline currentPolyline;
    private double distance;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = "OrderMapsService";

    private Location mLastKnownLocation;
    private final LatLng mDefaultLocation = new LatLng(41.7885889, -87.5997399);
    private boolean mLocationPermissionGranted;
    private LatLng startLocation = null;

    private Marker startPointMarker;
    private Marker stopPointMarker;
    private Marker myLocationMarker;

    private float DEFAULT_ZOOM = 15f;

    private Context mContext;
    private Activity mActivity;
    private List<LatLng> route = new ArrayList<>();


    public MapService(Context context, Activity activity, MapView mapView) {
        this.mContext = context;
        this.mActivity = activity;
        this.mapFragment = mapView;
    }

    public MapService(Context context, Activity activity, SupportMapFragment supportMapFragment) {
        this.mContext = context;
        this.mActivity = activity;
        this.supportMapFragment = supportMapFragment;
    }


    @Override
    public void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        setDistance(DataParser.distance);
    }

    public boolean GPSTurnedOn(){
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        this.GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return this.GpsStatus;
    }

    public void init(){

        if(!GPSTurnedOn()){
            new AlertDialog.Builder(mContext)
                    .setPositiveButton("Turn on.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No Thanks.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            @TODO: stop that app if user refuses
                        }
                    })
                    .setCancelable(true)
                    .setMessage("This app may need to\nn use your location.\n Please turn on location services.")
                    .show();

//            return;
        }

        int canUseMaps = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);

        if(canUseMaps != ConnectionResult.SUCCESS){
            Toast.makeText(mContext, "You can not fully use this app with no permissions", Toast.LENGTH_SHORT).show();
            return;
        }

//        inittialise geocoder
        geoCoder = new Geocoder(mContext, Locale.getDefault());

//        Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    public void drawMap(){
        mapFragment.getMapAsync(this);
    }

    public void drawMap(boolean inActivity){
        if(!inActivity){
            drawMap();
        }else {
            supportMapFragment.getMapAsync(this);
        }

    }
//    permissions

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {

//                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            LatLng currentLoc = null;
                            if (mLastKnownLocation != null) {
                                currentLoc = new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude());
                                fordgeRoute(5, currentLoc);
                            }else {
                                currentLoc = mDefaultLocation;
                            }

                            if (currentLoc != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc
                                        , DEFAULT_ZOOM));
                            }
                            if (currentLoc != null) {
//                                mMap.addMarker(new MarkerOptions().position(currentLoc)
//                                        .title("You are here"));

                                try {

                                    List<Address> addresses = geoCoder.getFromLocation(currentLoc.latitude, currentLoc.longitude, 1);
                                    if (addresses.isEmpty()) {
                                        Log.d(TAG,"Waiting for Location");
                                        myLocationMarker = mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(41.7885889, -87.5997399))
                                                .title("Your workout starts at " + addresses.get(0).getFeatureName())
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dropin_location))
                                        );
                                        fordgeRoute(5, new LatLng(41.7885889, -87.5997399));
                                        drawCompleteJoggingPath();
                                    }
                                    else {
                                        if (addresses.size() > 0) {
                                            myLocationMarker = mMap.addMarker(new MarkerOptions()
                                                    .position(currentLoc)
                                                    .title("Your workout starts at " + addresses.get(0).getFeatureName())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dropin_location))
                                            );

                                            fordgeRoute(5, currentLoc);
                                            drawCompleteJoggingPath();
                                        }
                                    }

                                    myLocationMarker.showInfoWindow();
                                } catch (IOException io){
                                    Log.e(TAG, io.getMessage());
                                }
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void fordgeRoute (int distanceInKm, LatLng startPoint){
//        Assuming triangular movement
        double perimeter = distanceInKm / 3;
        double perimeterLatDisplacement = perimeter / 110; // since every goe lat degree is 110kms on surface
        double perimeterLongDisplacement = perimeter / (111 * Math.cos(perimeterLatDisplacement)); // since every goe long degree is 111 (lats)ks on surface

        LatLng secondPoint = new LatLng((startPoint.latitude + perimeterLatDisplacement), startPoint.longitude);
        LatLng thirdPoint = new LatLng(secondPoint.latitude, secondPoint.longitude - perimeterLongDisplacement);
        LatLng forthPoint = new LatLng(startPoint.latitude - (perimeterLatDisplacement / distanceInKm), secondPoint.longitude - perimeterLongDisplacement - (perimeterLongDisplacement / distanceInKm));
        this.route.add(startPoint);
        this.route.add(secondPoint);
        this.route.add(thirdPoint);
        this.route.add(forthPoint);
    }

    public void removeMarker(MarkerOptions options){

    }

    public void addMarker(LatLng latLng, int type){

        if(myLocationMarker != null){
            myLocationMarker.remove();
        }

        if(type == START_POINT){
            startLocation = latLng;

            if(startPointMarker != null){
                startPointMarker.remove();
            }

            startPointMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your workout starts here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dropin_location))
            );

            startPointMarker.showInfoWindow();
        } else if (type == STOP_POINT) {

            if(stopPointMarker != null){
                stopPointMarker.remove();
            }

            stopPointMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your workout stops here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dropin_location))
            );

            stopPointMarker.showInfoWindow();
        } else {

            if(stopPointMarker != null){
                stopPointMarker.remove();
            }

            stopPointMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your workout stops here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dropin_location))
            );

            stopPointMarker.showInfoWindow();
        }

        if(startPointMarker != null && stopPointMarker != null){
//            @TODO: zoom to many points in screen
//            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(startLocation,latLng),50));
            zoomInToLocations(startPointMarker,stopPointMarker);
        }else{
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
        }
    }

    public void zoomInToLocations(Marker startPoint, Marker stopPoint){

        if(startPoint != null && stopPoint != null){
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(startPoint.getPosition());
            builder.include(stopPoint.getPosition());

            LatLngBounds bounds = builder.build();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,100);

            drawRoute(null, null);

            mMap.animateCamera(cameraUpdate);
        }
    }

    //    override ,maps API
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(6);
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    public void drawCompleteJoggingPath(){
        drawRoute(route.get(0), route.get(1));
        drawRoute(route.get(1), route.get(2));
        drawRoute(route.get(2), route.get(3));
        drawRoute(route.get(3), route.get(0));
    }

    public void drawRoute(LatLng start, LatLng stop){
        FetchURL fetchURL = new FetchURL(MapService.this);

        if (start != null){
            fetchURL.execute(getUrl(start,stop, "walking"), "walking");
        } else {
            fetchURL.execute(getUrl(startPointMarker.getPosition(), stopPointMarker.getPosition(), "walking"), "walking");
        }

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyC-ax9Y-q-R4HXbL7yoDEN1ywzV1d1coSM";
        return url;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
