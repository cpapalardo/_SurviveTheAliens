package com.ep4.survivethealiens.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.ep4.survivethealiens.Feign.Task.AtualizarDadosTask;
import com.ep4.survivethealiens.Helper.GpsTrackerHelper;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.meta.SimpleSubscriberInfo;

import java.text.DecimalFormat;
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
    MediaPlayer mp;
    ArrayList<LatLng> pontoList = new ArrayList<LatLng>();
    int times = 0;
    long tempoPassado;
    float distanciaEmMetros;
    boolean pausarMissao, introCompleta, apiceCompleta, missaoCompleta;
    float kmIntro, kmApice, kmConclusao;
    long conclusaoIntro, conclusaoApice, conclusao;
    Button buttonContinuar, buttonPausar;
    Jogador jogador;
    Location pontoAnterior = new Location("");
    DecimalFormat df = new DecimalFormat("0.##");
    public Missao missao;

    LocationManager locationManager;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missao);

        //se jogador for encontrado
        if(SaveSharedPreference.getId(this) == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        jogador = SaveSharedPreference.getJogador(this);

        missao = EventBus.getDefault().getStickyEvent(Missao.class);
        EventBus.getDefault().removeStickyEvent(Missao.class);
        pausarMissao = missao.isPausarMissao();
        introCompleta = missao.isIntroCompleta();
        apiceCompleta = missao.isApiceCompleta();
        missaoCompleta = missao.isMissaoCompleta();
        kmIntro = missao.getKmIntro();
        kmApice = missao.getKmApice();
        kmConclusao = missao.getKmFim();
        tempoPassado = missao.getTempoDecorrido();
        distanciaEmMetros = missao.getDistanciaPercorrida();

        infotext = (TextView) findViewById(R.id.infotext);
        textViewTempoValor = (TextView) findViewById(R.id.textViewTempoValor);
        chronometerTempoJogo = (Chronometer) findViewById(R.id.chronometerTempoJogo);
        buttonContinuar = (Button) findViewById(R.id.buttonContinuar);
        buttonPausar = (Button) findViewById(R.id.buttonPausar);

        //pegando fragmento do mapa
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        context = this;
        //classe que pega localização
        gpsTrackerHelper = new GpsTrackerHelper(this);

        textViewTempoValor.setText(String.valueOf(df.format(distanciaEmMetros)));

        //definindo em que momento o timer inicia e tempo para ser repetido
        timer.schedule(timerTask, 1000, 2000);

        //handler pegando latitude e longitude de uma List e desenhando no mapa a cada 5 segundos
        m_handler = new Handler();
        m_handlerTask = new Runnable()
        {
            @Override
            public void run() {
                if(times < pontoList.size()-1)
                {
                    LatLng src = pontoList.get(times);
                    LatLng dest = pontoList.get(times + 1);
                    Polyline line = mapa.addPolyline(new PolylineOptions()
                            .add(new LatLng(src.latitude, src.longitude),
                                    new LatLng(dest.latitude,dest.longitude))
                            .width(5).color(Color.MAGENTA).geodesic(true));
                    times++;
                    //Toast.makeText(context, (pontoList.get(pontoList.size()-1).toString()), Toast.LENGTH_LONG).show();
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

    //timer busca localização e envia para a lista pontoList.
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                if(!pausarMissao && isGPSEnabled && !missaoCompleta) {
                    if (gpsTrackerHelper.canGetLocation()) {
                        //double latitude = gpsTrackerHelper.getLatitude();
                        //double longitude = gpsTrackerHelper.getLongitude();
                        Location l = gpsTrackerHelper.getMyLocation();
                        //Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
                        //Log.d("PASSANDO LOCALIZAÇÃO", latitude + " " + longitude);
                        pontoList.add(new LatLng(l.getLatitude(), l.getLongitude()));

                        if (pontoAnterior != l) {
                            distanciaEmMetros += (pontoAnterior.distanceTo(l) / 1000);
                            pontoAnterior = l;
                        }
                    } else {
                        // Toast.makeText(context, "DEU RUIM NO ELSE", Toast.LENGTH_SHORT).show();
                        //Log.d("PASSANDO LOCALIZAÇÃO", "DEU RUIM NO ELSE");
                        gpsTrackerHelper.showSettingsAlert();
                    }

                    textViewTempoValor.setText(String.valueOf(df.format(distanciaEmMetros)));
                }
            } catch (Exception e) {
                System.err.println("DEU RUIM NO GPS");
                e.printStackTrace();
            } finally {
                /*for (int i = 0; i < pontoList.size()-1; i++) {
                    Location location = new Location("");
                    location.setLatitude(pontoList.get(i).latitude);
                    location.setLongitude(pontoList.get(i).longitude);

                    Location location2 = new Location("");
                    location2.setLatitude(pontoList.get(i+1).latitude);
                    location2.setLongitude(pontoList.get(i+1).longitude);

                    distanciaEmMetros += location.distanceTo(location2);
                }*/
            }

            //LoginActivity.distancia = distanciaEmMetros;
            if (isGPSEnabled) {
                if (missao.getId() == 1) {
                    if (distanciaEmMetros >= kmIntro && distanciaEmMetros < kmApice && !introCompleta) {
                        missao.setIntroCompleta(true);
                        introCompleta = true;
                        conclusaoIntro = chronometerTempoJogo.getBase();

                        mp = MediaPlayer.create(MissaoActivity.this, R.raw.missao_1_1);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                        mp.start();
                    } else if (distanciaEmMetros >= kmApice && distanciaEmMetros < kmConclusao && !apiceCompleta) {
                        missao.setApiceCompleta(true);
                        apiceCompleta = true;
                        conclusaoApice = chronometerTempoJogo.getBase();

                        mp = MediaPlayer.create(MissaoActivity.this, R.raw.missao_1_2);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                        mp.start();
                    } else if (distanciaEmMetros >= kmConclusao && !missaoCompleta) {
                        missao.setMissaoCompleta(true);
                        missaoCompleta = true;
                        chronometerTempoJogo.stop();
                        conclusao = chronometerTempoJogo.getBase() - SystemClock.elapsedRealtime();

                        mp = MediaPlayer.create(MissaoActivity.this, R.raw.missao_1_3);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                        mp.start();

                        atualizarDados();
                    }
                }
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        MapStyleOptions style;
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        //define o estilo do mapa conforme a hora do dia.
        if(timeOfDay >= 5 && timeOfDay < 18){
            style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro);
        }else{
            style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_night);
        }
        map.setMapStyle(style);

        //verifica se possui permissão para usar localização
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            isGPSEnabled = false;
            buildAlertMessageNoGps();
        } else {
            isGPSEnabled = true;
        }

        if (isGPSEnabled) {
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,10, ));
            Criteria criteria = new Criteria();

            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            location = getLastKnownLocation();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(), location.getLongitude())), 15));
            if (location == null) {
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                // Finds a provider that matches the criteria
                String provider = locationManager.getBestProvider(criteria, true);
                // Use the provider to get the last known location
                location = locationManager.getLastKnownLocation(provider);
            }
            if (location != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            //iniciando cronômetro
            chronometerTempoJogo.setBase(missao.getTempoDecorrido() + SystemClock.elapsedRealtime());
            if (missao.isMissaoCompleta()) {
                buttonPausar.setEnabled(false);
                buttonContinuar.setEnabled(false);
            } else {
                if (!pausarMissao) {
                    chronometerTempoJogo.start();
                } else {
                    buttonPausar.setEnabled(false);
                    buttonContinuar.setEnabled(true);
                }
            }
            pontoAnterior.setLongitude(location.getLongitude());
            pontoAnterior.setLatitude(location.getLatitude());
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Seu GPS não está ativado. Deseja ativá-lo?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, PrincipalActivity.class);
                        startActivity(intent);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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

    public void atualizarDados(){
        //parando as threads?
        timer.cancel();
        m_handler.removeCallbacks(m_handlerTask);
        //atualizando jogador e missaoJogador
        float distanciaTotal = distanciaEmMetros + jogador.getKmCaminhados();
        missao.setConcluida(true);
        jogador.setHorasJogadas(conclusao + jogador.getHorasJogadas());
        jogador.setKmCaminhados(distanciaTotal + jogador.getKmCaminhados());
        new AtualizarDadosTask(this, this).execute(jogador);
    }

    public void pausar(View view){
        pausarMissao = true;
        tempoPassado = chronometerTempoJogo.getBase() - SystemClock.elapsedRealtime();
        chronometerTempoJogo.stop();
        buttonContinuar.setEnabled(true);
        buttonPausar.setEnabled(false);
        missao.setPausarMissao(pausarMissao);

        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
    }

    public void continuar(View view){
        pausarMissao = false;
        chronometerTempoJogo.setBase(tempoPassado + SystemClock.elapsedRealtime());
        chronometerTempoJogo.start();
        buttonContinuar.setEnabled(false);
        buttonPausar.setEnabled(true);
        missao.setPausarMissao(pausarMissao);

        if (mp != null) {
            mp.start();
        }
    }

    @Override
    public void onBackPressed() {
        missao.setTempoDecorrido(chronometerTempoJogo.getBase() - SystemClock.elapsedRealtime());
        missao.setDistanciaPercorrida(distanciaEmMetros);
        missao.setPausarMissao(pausarMissao);
        missao.setIntroCompleta(introCompleta);
        missao.setApiceCompleta(apiceCompleta);
        missao.setMissaoCompleta(missaoCompleta);
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.getAudioSessionId();
                mp.reset();
            }
        }
        super.onBackPressed();
    }
}