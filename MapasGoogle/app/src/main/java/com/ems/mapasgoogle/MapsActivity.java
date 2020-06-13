package com.ems.mapasgoogle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private EditText endereco;
    private Button btBusca;
    private TextView dist;
    Double lat, lon;
    Double latAtual, lonAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // solicita a permissão para habilitar a geolocalização
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[1];
            permissions[0] = permission;
            ActivityCompat.requestPermissions(this, permissions, 1);
        }

        endereco = findViewById(R.id.query);
        dist = findViewById(R.id.dist);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // mostra um campo para realizar pesquisas
        endereco.setText("03590120");
        endereco.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        busca(endereco.getText().toString());
                        endereco.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void busca(String endereco) throws IOException {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(endereco, 1);
            lat = (double) (addresses.get(0).getLatitude());
            lon = (double) (addresses.get(0).getLongitude());

            LatLng local = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(local).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Local solicitado"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 18));

            // recupera o novo posicionamento
            LatLng latLong = new LatLng(lat, lon);

            // Calcula a distância entre a origem (posição atual) e o destino
            Location selected_location = new Location("locationA");
            selected_location.setLatitude(latAtual);
            selected_location.setLongitude(lonAtual);
            Location near_locations = new Location("locationB");
            near_locations.setLatitude(lat);
            near_locations.setLongitude(lon);
            double distance = selected_location.distanceTo(near_locations);

            String distancia = new String();
            if (distance > 1000) {
                distancia = String.valueOf((int) distance / 1000) + " kilômetros";
            } else {
                distancia = String.valueOf((int) distance) + " metros";
            }

            // Adiciona uma linha reta entre os pontos inicial e o final
            PolygonOptions rectOptions = new PolygonOptions();
            rectOptions.add(new LatLng(lat, lon));
            rectOptions.add(new LatLng(latAtual, lonAtual));
            mMap.addPolygon(rectOptions);

            dist.setText(distancia);
        } catch (Exception e) {
            Toast.makeText(this, "Endereço não localizado", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        try {
            // permite refinar o sistema de localização
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = locationManager.getBestProvider(criteria, true);

            // configurações do mapa
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            UiSettings settings = mMap.getUiSettings();
            settings.setCompassEnabled(false);
            settings.setRotateGesturesEnabled(true);
            settings.setScrollGesturesEnabled(true);
            settings.setZoomControlsEnabled(true);
            settings.setZoomGesturesEnabled(true);

            // habilita a localização atual
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
            double lat = lastKnownLocation.getLatitude();
            double lon = lastKnownLocation.getLongitude();

            latAtual = lat;
            lonAtual = lon;

            // posiciona o marcador na posição onde o acesso está sendo realizado
            LatLng localAtual = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(localAtual).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Você está aqui"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localAtual, 18));

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onMapClick(LatLng latLong) {
        // Adiciona um novo marcador ao clicar (pode ser guardado no banco de dados para uso posterior)
        LatLng localAtual = new LatLng(latLong.latitude, latLong.longitude);
        mMap.addMarker(new MarkerOptions().position(localAtual).icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }
}