package com.example.myloop;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myloop.fragments.SegmentSheet;
import com.example.myloop.helpers.MapService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG ="MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private GoogleMap gMap;
    Location mLastLocation;

    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private SupportMapFragment mapFragment;
    private LocationCallback mLocationCallback;
    private List<Polyline> polylines;
    private final int REQUEST_CODE = 20;
    Marker mCurrLocationMarker;
    private int MAP_LOADED = 0;
    int myId = 0;

    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFDref;

    private MapService mapService;
    private SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExplore = findViewById(R.id.btn_explore);

        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SegmentSheet segmentSheet = new SegmentSheet();
                segmentSheet.showNow(getSupportFragmentManager(), "segment");
            }
        });

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapService = new MapService(getApplicationContext(), this, supportMapFragment);
        mapService.init();
        mapService.drawMap(true);
    }

    //TO-DO
    //create info window for nearby cycles
    //https:developers.google.com/maps/documentation/android-sdk/marker
}