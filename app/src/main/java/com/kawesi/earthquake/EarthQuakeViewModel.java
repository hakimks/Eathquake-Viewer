package com.kawesi.earthquake;

import android.app.Application;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class EarthQuakeViewModel extends AndroidViewModel {
    private static final String TAG = "EarthQuakeViewModel";
    private MutableLiveData<List<Earthquake>> earthquakes;

    public EarthQuakeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Earthquake>> getEarthQuakes(){
        if (earthquakes == null){
            earthquakes = new MutableLiveData<List<Earthquake>>();
            loadEarthQuakes();
        }
        return earthquakes;
    }

    // Asynchronously load the Earthquakes from the feed.
    public void loadEarthQuakes() {
        new AsyncTask<Void, Void, List<Earthquake> >(){
            @Override
            protected List<Earthquake> doInBackground(Void... voids) {
                ArrayList<Earthquake> earthquakes = new ArrayList<>(0);

                // get the xml
                URL url;
                try{
                    String quakefeed = getApplication().getString(R.string.eathquate_feed);
                    url = new URL(quakefeed);
                    URLConnection connection = url.openConnection();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                    int resposeCode = httpURLConnection.getResponseCode();
                    if (resposeCode == HttpURLConnection.HTTP_OK){
                        InputStream in = httpURLConnection.getInputStream();
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();

                        // parse the quake feed
                        Document dom = db.parse(in);
                        Element docElement = dom.getDocumentElement();

                        // Get a list of each earthquake entry
                        NodeList nl = docElement.getElementsByTagName("entry");

                        if (nl != null && nl.getLength() > 0){
                            for (int i = 0; i < nl.getLength(); i++){
                                // Check to see if our loading has been cancelled, in which
                                // case return what we have so far.
                                if (IsCancelled()){
                                    Log.d(TAG, "Loading Cancelled");
                                    return earthquakes;
                                }
                                Element entry = (Element)nl.item(i);
                                Element id = (Element)entry.getElementsByTagName("id").item(0);
                                Element title = (Element)entry.getElementsByTagName("title").item(0);
                                Element g = (Element)entry.getElementsByTagName("georss:point")
                                                .item(0);
                                Element when = (Element)entry.getElementsByTagName("updated").item(0);
                                Element link = (Element)entry.getElementsByTagName("link").item(0);
                                String idString = id.getFirstChild().getNodeValue();
                                String details = title.getFirstChild().getNodeValue();
                                String hostname = "http://earthquake.usgs.gov";
                                String linkString = hostname + link.getAttribute("href");
                                String point = g.getFirstChild().getNodeValue();
                                String dt = when.getFirstChild().getNodeValue();
                                SimpleDateFormat sdf =
                                        new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                                Date qdate = new GregorianCalendar(0,0,0).getTime();
                                try {
                                    qdate = sdf.parse(dt);
                                } catch (ParseException e) {
                                    Log.e(TAG, "Date parsing exception.", e);
                                }

                                String[] location = point.split(" ");
                                Location l = new Location("dummyGPS");
                                l.setLatitude(Double.parseDouble(location[0]));
                                l.setLongitude(Double.parseDouble(location[1]));
                                String magnitudeString = details.split(" ")[1];
                                int end = magnitudeString.length()-1;
                                double magnitude =
                                        Double.parseDouble(magnitudeString.substring(0, end));
                                if (details.contains("-"))
                                    details = details.split("-")[1].trim();
                                else
                                    details = "";
                                final Earthquake earthquake = new Earthquake(idString,
                                        qdate,
                                        details, l,
                                        magnitude,
                                        linkString);
                                // Add the new earthquake to our result array.
                                earthquakes.add(earthquake);


                            }
                        }

                    }
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e){
                    e.printStackTrace();

                } catch (IOException e){
                    e.printStackTrace();

                } catch (ParserConfigurationException e){
                    e.printStackTrace();

                }catch (SAXException ex){
                    ex.printStackTrace();

                }
                return earthquakes;
            }


            @Override
            protected void onPostExecute(List<Earthquake> data) {
                // update live data with the new list
                earthquakes.setValue(data);

            }
        }.execute();

    }

    private boolean IsCancelled() {
        return false;
    }
}
