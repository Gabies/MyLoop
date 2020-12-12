package com.example.myloop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloop.LoginActivity;
import com.example.myloop.R;
import com.example.myloop.SegmentAdapter;
import com.example.myloop.models.Trail;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    public static final String TAG = "ExploreFragment";
    protected RecyclerView rvSegments;
    protected SegmentAdapter adapter;
    protected List<Trail> allSegments;

    public ExploreFragment() {
        // Required empty public constructor
    }

    //The onCreateView method is called when Fragment should create its View object hierarchy,
    //either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    //This event is triggered soon after onCreateView().
    //Any view setup should occur here. Eg view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSegments = view.findViewById(R.id.rvSegments);
        allSegments = new ArrayList<>();
        //create the adapter
        adapter = new SegmentAdapter(getContext(), allSegments);
        //set adapter to the recycler view
        rvSegments.setAdapter(adapter);
        //set the layout manager to the recycler view
        rvSegments.setLayoutManager(new LinearLayoutManager(getContext()));

//        final Button logout = view.findViewById(R.id.logoutButton);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick logout button");
//                ParseUser.logOut();
//                ParseUser currentUser = ParseUser.getCurrentUser(); //this will now be null
//                //goLogin();
//            }
//        });
    }

//    private void goLogin() {
//        startActivity(new Intent(this, LoginActivity.class));
//    }
}
