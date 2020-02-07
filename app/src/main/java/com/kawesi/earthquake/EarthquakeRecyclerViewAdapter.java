package com.kawesi.earthquake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kawesi.earthquake.databinding.ListItemEathquakeBinding;

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
        ListItemEathquakeBinding binding = ListItemEathquakeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Earthquake earthquake = mEarthquakes.get(position);
        holder.binding.setEarthquake(earthquake);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mEarthquakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final ListItemEathquakeBinding binding;

        public ViewHolder(ListItemEathquakeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setTimeformat(TIME_FORMART);
            binding.setMagnituteformat(MAGNITUTE_FORMAT);

        }

    }
}
