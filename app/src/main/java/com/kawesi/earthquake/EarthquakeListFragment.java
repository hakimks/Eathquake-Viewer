package com.kawesi.earthquake;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    protected EarthQuakeViewModel earthquakeViewModel;
    private SwipeRefreshLayout mSwipeToRefreshView;

    private OnListFragmentInteractionListener mListener;


    public EarthquakeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_earthquake_list, container, false);
        mSwipeToRefreshView = view.findViewById(R.id.swipeToRefresh);
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

        // set up swipe to refresh
        mSwipeToRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateEarthQuakes();
            }
        });
    }

    private void updateEarthQuakes() {
        if (mListener == null){
            mListener.onListFragmentRefreshRequested();
        }

    }

    public void setEarthquakes(List<Earthquake> earthquakes){
        mEarthquakes.clear();
        mEarthAdapter.notifyDataSetChanged();
        for (Earthquake earthquake: earthquakes) {
            if (!mEarthquakes.contains(earthquake)){
                mEarthquakes.add(earthquake);
                mEarthAdapter.notifyItemInserted(mEarthquakes.indexOf(earthquake));
            }
            
        }
        mSwipeToRefreshView.setRefreshing(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Retrieve the Earthquake View Model for the parent Activity.
        earthquakeViewModel = new ViewModelProvider(this).get(EarthQuakeViewModel.class);

        // Get the data from the View Model, and observe any changes.
        earthquakeViewModel.getEarthQuakes()
                .observe(this, new Observer<List<Earthquake>>() {
                    @Override
                    public void onChanged(List<Earthquake> earthquakes) {
                        if (earthquakes == null){
                            setEarthquakes(earthquakes);
                        }
                    }
                });
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentRefreshRequested();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnListFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
