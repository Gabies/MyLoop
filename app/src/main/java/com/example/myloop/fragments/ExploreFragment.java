package com.example.myloop.fragments;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

//import com.example.myloop.Manifest;
import com.example.myloop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {
    public static final String TAG = "ExploreFragment";

    private Button btnLocation;
    private double lat;
    private double lng;
    FusedLocationProviderClient fusedLocationProviderClient;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnLocation = view.findViewById(R.id.btnLocation);

        /* Initialize fusedLocation provider client */
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //get location here
                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location !=null) {
                                            lat = location.getLatitude();
                                            lng = location.getLongitude();
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }  else {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
            }

//            @Override
//            public void onClick(View v) {
//                // check permission
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                } else {
//                    ActivityCompat.requestPermissions(getApplicationCont(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//                }
////
//            }
        });
    }

//    private void getLocation() {
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                //Initialize location
//                Location location = task.getResult();
//                if (location != null) {
//                    try {
//                        //Initialize geocoder
//                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//                        //Initialize  address list
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
//                        lat = addresses.get(0).getLatitude();
//                        lng = addresses.get(0).getLongitude();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

}
