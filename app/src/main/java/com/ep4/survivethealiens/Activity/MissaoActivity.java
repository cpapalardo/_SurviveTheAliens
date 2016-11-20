package com.ep4.survivethealiens.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.ep4.survivethealiens.Helper.GpsTrackerHelper;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MissaoActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener/*, LocationListener, GoogleApiClient.ConnectionCallbacks*/{
    MapFragment mapFragment;
    GoogleMap mapa;
    Location location;
    GpsTrackerHelper gpsTrackerHelper;
    TextView infotext, textViewTempoValor;
    Context context;
    Handler m_handler;
    Runnable m_handlerTask;
    Chronometer chronometerTempoJogo;
    boolean isGPSEnabled = false;

    ArrayList<LatLng> pontoList = new ArrayList<LatLng>();
    int times=0;
    long tempoPassado = 0;
    float distanciaEmMetros = LoginActivity.distancia;
    boolean pausarMissao = false;
    long kmIntro, kmApice, kmConclusao;
    long conclusaoIntro, conclusaoApice, conclusao;

    LocationManager locationManager;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missao);

        //se jogador for encontrado
        if(SaveSharedPreference.getId(this).length() == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        infotext = (TextView) findViewById(R.id.infotext);
        textViewTempoValor = (TextView) findViewById(R.id.textViewTempoValor);
        chronometerTempoJogo = (Chronometer) findViewById(R.id.chronometerTempoJogo);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        context = this;
        gpsTrackerHelper = new GpsTrackerHelper(this);

        textViewTempoValor.setText(String.valueOf(LoginActivity.distancia));
        timer.schedule(timerTask, 2000, 5000);
        chronometerTempoJogo.start();

        m_handler = new Handler();
        m_handlerTask = new Runnable()
        {
            @Override
            public void run() {
                if(times<pontoList.size()-1)
                {
                    LatLng src = pontoList.get(times);
                    LatLng dest = pontoList.get(times + 1);
                    Polyline line = mapa.addPolyline(new PolylineOptions()
                            .add(new LatLng(src.latitude, src.longitude),
                                    new LatLng(dest.latitude,dest.longitude))
                            .width(5).color(Color.MAGENTA).geodesic(true));
                    times++;
                    Toast.makeText(context, (pontoList.get(pontoList.size()-1).toString()), Toast.LENGTH_LONG).show();
                }
                else
                {
                    m_handler.removeCallbacks(m_handlerTask);
                }
                m_handler.postDelayed(m_handlerTask, 5000);
            }
        };
        m_handlerTask.run();
    }

    Timer timer = new Timer();


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                if(!pausarMissao && isGPSEnabled) {
                    if (gpsTrackerHelper.canGetLocation()) {
                        double latitude = gpsTrackerHelper.getLatitude();
                        double longitude = gpsTrackerHelper.getLongitude();
                        Location l = gpsTrackerHelper.getMyLocation();
                        //Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                        Log.d("PASSANDO LOCALIZAÇÃO", latitude + " " + longitude);
                        pontoList.add(new LatLng(l.getLatitude(), l.getLongitude()));
                    } else {
                        // Toast.makeText(context, "DEU RUIM NO ELSE", Toast.LENGTH_SHORT).show();
                        Log.d("PASSANDO LOCALIZAÇÃO", "DEU RUIM NO ELSE");
                        gpsTrackerHelper.showSettingsAlert();
                    }
                }

                LoginActivity.distancia = distanciaEmMetros;
                textViewTempoValor.setText(String.valueOf(LoginActivity.distancia));

                //código com posição possivelmente incorreta
                if (distanciaEmMetros >= kmIntro && distanciaEmMetros < kmApice) {
                    conclusaoIntro = chronometerTempoJogo.getBase();

                } else if (distanciaEmMetros >= kmApice && distanciaEmMetros < kmConclusao) {
                    conclusaoApice = chronometerTempoJogo.getBase();

                } else if (distanciaEmMetros >= kmConclusao) {
                    conclusao = chronometerTempoJogo.getBase();

                }
            } catch (Exception e) {
                System.err.println("DEU RUIM NO GPS");
                e.printStackTrace();
            } finally {

                for(int i = 0; i < pontoList.size()-1; i++){
                    Location location = new Location("");
                    location.setLatitude(pontoList.get(i).latitude);
                    location.setLongitude(pontoList.get(i).longitude);

                    Location location2 = new Location("");
                    location2.setLatitude(pontoList.get(i+1).latitude);
                    location2.setLongitude(pontoList.get(i+1).longitude);

                    distanciaEmMetros += location.distanceTo(location2);
                }

            }
        }
    };


    private final Runnable textRunnable = new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    if(!pausarMissao && isGPSEnabled) {
                        if (gpsTrackerHelper.canGetLocation()) {
                            double latitude = gpsTrackerHelper.getLatitude();
                            double longitude = gpsTrackerHelper.getLongitude();
                            Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "DEU RUIM NO ELSE", Toast.LENGTH_SHORT).show();
                            gpsTrackerHelper.showSettingsAlert();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("DEU RUIM NO GPS");
                    e.printStackTrace();
                } finally {
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
            isGPSEnabled = true;
        } else {
            // Requisição de localização.
            isGPSEnabled = false;
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
            Toast.makeText(this, "Por favor, ative a localização.", Toast.LENGTH_LONG).show();
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

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

    @Override
    public void onLocationChanged(Location location) {

    }

    public void pausar(View view){
        pausarMissao = false;
        tempoPassado = chronometerTempoJogo.getBase() - SystemClock.elapsedRealtime();
        chronometerTempoJogo.stop();
    }

    public void continuar(View view){
        pausarMissao = true;
        chronometerTempoJogo.setBase(tempoPassado + SystemClock.elapsedRealtime());
        chronometerTempoJogo.start();
    }
}
