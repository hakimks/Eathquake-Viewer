package com.kawesi.earthquake;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarthquakeListFragment extends Fragment {
    private ArrayList<Earthquake> mEarthquakes = new ArrayList<Earthquake>();
    private RecyclerView mRecyclerView;
    private EarthquakeRecyclerViewAdapter mEarthAdapter = new EarthquakeRecyclerViewAdapter(mEarthquakes);


    public EarthquakeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_earthquake_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // set the recycler view adapter
        Context context = getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mEarthAdapter);
    }

    public void setEarthquakes(List<Earthquake> earthquakes){
        for (Earthquake earthquake: earthquakes) {
            if (!mEarthquakes.contains(earthquake)){
                mEarthquakes.add(earthquake);
                mEarthAdapter.notifyItemChanged(mEarthquakes.indexOf(earthquake));
            }
            
        }
    }
}
