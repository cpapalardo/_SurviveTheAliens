package com.ep4.survivethealiens;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aluno on 07/10/2016.
 */
public class MissaoActivity extends AppCompatActivity implements OnMapReadyCallback/*, LocationListener, GoogleApiClient.ConnectionCallbacks*/{

    MapFragment mapFragment;
    GoogleMap mapa;
    Location oldLocation, location;
    String mLastUpdateTime;
    GoogleApiClient client;
    GpsTrackerHelper gpsTrackerHelper;
    private static final Handler handler = new Handler();
    Context context;

    LocationManager locationManager;
    LocationRequest locationRequest;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missao);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
//        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
//        mapFragment.getMapAsync(this);
        context = this;
        gpsTrackerHelper = new GpsTrackerHelper(this);
        //handler.postDelayed(textRunnable, 10000);
        //textRunnable.run();
        timer.schedule(timerTask, 2000, 5000);
    }

    public void onConnected(){

    }
    Timer timer = new Timer();


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                if (gpsTrackerHelper.canGetLocation()) {
                    double latitude = gpsTrackerHelper.getLatitude();
                    double longitude = gpsTrackerHelper.getLongitude();
                    Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "DEU RUIM NO ELSE", Toast.LENGTH_SHORT).show();
                    gpsTrackerHelper.showSettingsAlert();
                }
            } catch (Exception e) {
                System.err.println("DEU RUIM NO GPS");
                e.printStackTrace();
            } finally {
                //Thread.sleep(10000);
                //SystemClock.sleep(10000);
            }
        }
    };


    private final Runnable textRunnable = new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    if (gpsTrackerHelper.canGetLocation()) {
                        double latitude = gpsTrackerHelper.getLatitude();
                        double longitude = gpsTrackerHelper.getLongitude();
                        Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "DEU RUIM NO ELSE", Toast.LENGTH_SHORT).show();
                        gpsTrackerHelper.showSettingsAlert();
                    }
                } catch (Exception e) {
                    System.err.println("DEU RUIM NO GPS");
                    e.printStackTrace();
                } finally {
                    //Thread.sleep(10000);
                    //SystemClock.sleep(10000);
                }
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap map) {

        mapa = map;
        LatLng sydney = new LatLng(-33.867, 151.206);
        MapStyleOptions style;
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 5 && timeOfDay < 18){
            style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro);
        }else{
            style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_night);
        }

        map.setMapStyle(style);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            Toast.makeText(this, "Por favor, ative a localização.", Toast.LENGTH_LONG).show();
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        //map.setMyLocationEnabled(true);
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

       // map.addMarker(new MarkerOptions().title("Sydney").snippet("The most populous city in Australia.").position(sydney));
       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.867, 151.206), 18));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,10, ));
        Criteria criteria = new Criteria();

        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        location = getLastKnownLocation();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(), location.getLongitude())), 15));
        if(location == null)
        {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = locationManager.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            location = locationManager.getLastKnownLocation(provider);
        }
        if (location != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private Location getLastKnownLocation() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            {
                locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                List<String> providers = locationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    Location l = locationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        bestLocation = l;
                        //return bestLocation;
                    }
                }
                return bestLocation;
            }
        }
        return null;
    }

//    private Double getCurrentSpeed(long now, long maxAge) {
//        if (oldLocation == null)
//            return null;
//        if (now > oldLocation.getTime() + maxAge)
//            return null;
//        double speed = oldLocation.getSpeed();
//        if ((!oldLocation.hasSpeed() || speed == 0.0f)
//                && oldLocation != null && location != null &&
//                oldLocation.getTime() > location.getTime() ) {
//            //Some Android (at least emulators) do not implement getSpeed() (even if hasSpeed() is true)
//            speed = oldLocation.distanceTo(location) * 1000 / (oldLocation.getTime() - location.getTime());
//        }
//        return speed;
//    }

//    protected void startLocationUpdates() {
//        client = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .build();
//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            ||(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
//            LocationServices.FusedLocationApi.requestLocationUpdates(
//                client, locationRequest, this);
//    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
}
