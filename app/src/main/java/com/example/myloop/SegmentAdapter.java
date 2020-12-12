package com.example.myloop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloop.models.Trail;
import com.google.android.gms.maps.MapView;

import java.util.List;

public class SegmentAdapter extends RecyclerView.Adapter<SegmentAdapter.ViewHolder>{
    private Context context;
    List<Trail> segments;

    public SegmentAdapter(Context context, List<Trail> segments){
        this.context = context;
        this.segments = segments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.segments_nearby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SegmentAdapter.ViewHolder holder, int position) {
        Trail segment = segments.get(position);
        holder.bind(segment);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MapView map;
        private final TextView name;
        private final TextView distance;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            map = itemView.findViewById(R.id.mapView);
            name = itemView.findViewById(R.id.tvTrail);
            distance = itemView.findViewById(R.id.ivDistance);
        }

        public void bind(Trail segment) {
            name.setText(segment.getTrailDescription());
            distance.setText(String.valueOf(segment.getTrailDistance()));
        }
    }
}
