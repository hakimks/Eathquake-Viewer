package com.kawesi.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EarthquakeListFragment.OnListFragmentInteractionListener {

    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    EarthquakeListFragment mEarthquakeListFragment;
    EarthQuakeViewModel mEarthQuakeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Android will automatically re-add any Fragments that
        // have previously been added after a configuration change,
        // so only add it if this isn't an automatic restart.
        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mEarthquakeListFragment = new EarthquakeListFragment();
            fragmentTransaction.add(R.id.main_activity_frame, mEarthquakeListFragment, TAG_LIST_FRAGMENT);
            fragmentTransaction.commitNow();

        } else {
            mEarthquakeListFragment = (EarthquakeListFragment)fragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT);
        }

        // Retrieve the Earthquake View Model for this Activity.
        mEarthQuakeViewModel = new ViewModelProvider(this).get(EarthQuakeViewModel.class);
    }

    @Override
    public void onListFragmentRefreshRequested() {
        updateEathQuakes();
    }

    private void updateEathQuakes() {
        // Request the View Model update the earthquakes from the USGS feed.
        mEarthQuakeViewModel.loadEarthQuakes();
    }
}
