package com.example.myloop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myloop.models.Route;
import com.google.android.gms.maps.MapView;

import java.util.List;

public class SegmentAdapter extends RecyclerView.Adapter<SegmentAdapter.ViewHolder>{
    private final Context context;
    List<Route> segments;

    public SegmentAdapter(Context context, List<Route> segments){
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
        Route segment = segments.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.route_1)
                .error(R.drawable.route_1)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        holder.name.setText(segment.getName());
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(segment.getImage()).into(holder.map);
        holder.distance.setText(String.valueOf(segment.getDistance()) + "mi");
    }

    @Override
    public int getItemCount() {
        return segments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView distance;
        private final ImageView map;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvTrail);
            distance = itemView.findViewById(R.id.ivDistance);
            map = itemView.findViewById(R.id.mapView);
        }
    }
}
