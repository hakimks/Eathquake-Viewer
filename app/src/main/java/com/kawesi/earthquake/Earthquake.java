package com.kawesi.earthquake;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Earthquake {
    private String mId;
    private Date mDate;
    private String mDetails;
    private Location mLocation;
    private double mMagnitute;
    private String mLink;

    public Earthquake(String id, Date date, String details, Location location, double magnitute, String link) {
        mId = id;
        mDate = date;
        mDetails = details;
        mLocation = location;
        mMagnitute = magnitute;
        mLink = link;
    }

    public String getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public String getMdetails() {
        return mDetails;
    }

    public Location getLocation() {
        return mLocation;
    }

    public double getMagnitute() {
        return mMagnitute;
    }

    public String getLink() {
        return mLink;
    }

    @Override
    public String toString(){
        SimpleDateFormat  sdf = new SimpleDateFormat("HH.mm", Locale.US);
        String dateString = sdf.format(mDate);
        return dateString + ": " + mMagnitute + " " + mDetails;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof  Earthquake){
            return ((Earthquake)obj).getId().contentEquals(mId);
        } else {
            return false;
        }
    }
}
