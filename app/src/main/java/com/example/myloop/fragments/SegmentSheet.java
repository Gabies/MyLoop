package com.example.myloop.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloop.R;
import com.example.myloop.SegmentAdapter;
import com.example.myloop.models.Route;
import com.example.myloop.models.Trail;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SegmentSheet extends BottomSheetDialogFragment {



    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_explore, null);

        dialog.setContentView(view);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });

            RecyclerView rvSegment = view.findViewById(R.id.rvSegments);
            TextView tvNoSuggestions = view.findViewById(R.id.tv_no_suggestions);

            List<Route> suggestions = new ArrayList<>();

            suggestions.add(new Route(getContext().getResources().getDrawable(R.drawable.route_1), 1.3f, null, null));
            suggestions.add(new Route(getContext().getResources().getDrawable(R.drawable.route_2), 0.7f, null, null));
            suggestions.add(new Route(getContext().getResources().getDrawable(R.drawable.route_3), 3.2f, null, null));
            suggestions.add(new Route(getContext().getResources().getDrawable(R.drawable.route_4), 3.2f, null, null));

            if (suggestions.isEmpty()){
                tvNoSuggestions.setVisibility(View.VISIBLE);
            }

            SegmentAdapter segmentAdapter = new SegmentAdapter(getContext(), suggestions);
            rvSegment.setHasFixedSize(true);
            rvSegment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rvSegment.setAdapter(segmentAdapter);

        }
    }
}
