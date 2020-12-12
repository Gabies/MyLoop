package com.example.myloop.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

//import com.example.myloop.Manifest;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.myloop.R;
import com.example.myloop.StravaApi;
import com.example.myloop.models.Segment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {
    public static final String TAG = "ExploreFragment";

    private Button btnLocation;
    private TextView tvViewResult;

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

        Uri intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", "57060")
                .appendQueryParameter("redirect_uri", "http://localhost")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("approval_prompt", "auto")
                .appendQueryParameter("scope", "activity:write,read")
                .build();

        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH, intentUri);
        startActivity(intent);

        String respond =  intent.getAction();
        Uri data = intent.getData();

        Log.d(TAG, data + " " + respond);


//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("client_id", "57060");
//        params.put("redirect_uri", "http://localhost");
//        params.put("response_type", "code");
//        params.put("approval_prompt", "auto");
//        params.put("scope", "activity:write,read");
//        client.get("https://www.strava.com/oauth/mobile/authorize", params, new TextHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Headers headers, String s) {
//                Log.d(TAG, s);
//            }
//
//            @Override
//            public void onFailure(int i, @Nullable Headers headers, String s, @Nullable Throwable throwable) {
//                Log.d(TAG, "Failed", throwable);
//            }
//        });


        //TODO: Token exchange: get the authCode from the request and POST https://www.strava.com/oauth/token


        //TODO:
//        btnLocation = view.findViewById(R.id.btnLocation);
//        /* Initialize fusedLocation provider client */
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//        btnLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        /* Get the current location */
//                        fusedLocationProviderClient.getLastLocation()
//                                .addOnSuccessListener(new OnSuccessListener<Location>() {
//                                    @Override
//                                    public void onSuccess(Location location) {
//                                        if (location !=null) {
//
//                                            lat = location.getLatitude();
//                                            lng = location.getLongitude();
//                                            tvViewResult.append(String.valueOf(lat));
//                                            tvViewResult.append(String.valueOf(lng));
//
//                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }  else {
//                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                    }
//                }
//            }
//

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
//        });

        tvViewResult = view.findViewById(R.id.tvViewResults);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.strava.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /* Initialize the segment where Retrofit automatically generate the stubs in segment interface */
        StravaApi stravaApi = retrofit.create(StravaApi.class);
        Float[] bounds;
        bounds = new Float[]{37.8331119f, -122.4834356f, 37.8280722f, -122.4981393f};

        Call<List<Segment>> call = stravaApi.getExploreSegment(bounds, "running", 56, 56);

        // Retrofit will take care of creating the thread another thread
        call.enqueue(new Callback<List<Segment>>() {
            @Override
            public void onResponse(Call<List<Segment>> call, Response<List<Segment>> response) {
                if (!response.isSuccessful()) {
                    tvViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Segment> segments = response.body();

                for (Segment segment : segments) {
                    String points = " " + segment.getPoints() + "\n";
                    tvViewResult.append(points);
                }
            }

            @Override
            public void onFailure(Call<List<Segment>> call, Throwable t) {
                tvViewResult.setText(t.getMessage());
            }
        });

    }

    private static List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
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
