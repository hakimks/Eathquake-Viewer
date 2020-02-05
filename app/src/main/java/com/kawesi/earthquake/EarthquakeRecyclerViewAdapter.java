package com.kawesi.earthquake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EarthquakeRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder> {
    private final List<Earthquake> mEarthquakes;
    private static final SimpleDateFormat TIME_FORMART = new SimpleDateFormat("HH:mm", Locale.US);
    private static final NumberFormat MAGNITUTE_FORMAT = new DecimalFormat("0.0");

    public EarthquakeRecyclerViewAdapter(List<Earthquake> earthquakes){
        mEarthquakes = earthquakes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_eathquake, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Earthquake earthquake = mEarthquakes.get(position);
        holder.date.setText(TIME_FORMART.format(earthquake.getDate()));
        holder.magnitute.setText(MAGNITUTE_FORMAT.format(earthquake.getMagnitute()));
        holder.details.setText(earthquake.getMdetails());
    }

    @Override
    public int getItemCount() {
        return mEarthquakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView details;
        public final TextView date;
        public final TextView magnitute;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            details = (TextView) itemView.findViewById(R.id.details);
            date = (TextView) itemView.findViewById(R.id.date);
            magnitute = (TextView) itemView.findViewById(R.id.magnitude);
        }

    }
}
