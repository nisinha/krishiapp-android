package net.krishi.krishiapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import net.krishi.krishiapp.model.Cordinates;
import net.krishi.krishiapp.model.MapDetails;

import java.util.ArrayList;
import java.util.List;

public class FarmCropDetail extends AppCompatActivity {

    private MapView mapView;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private MapDetails mapDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MapboxAccountManager.start(this, "pk.eyJ1IjoibmlzaGFudGtzIiwiYSI6ImNpcnY4NGpjazBpYWt0Y25rMWxsdWsycW0ifQ.PAl_I8-5kezrQYun_Z2xzQ");
        setContentView(R.layout.activity_farm_crop_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String details = extras.getString("MapDetails");
            mapDetails = new Gson().fromJson(details, MapDetails.class);

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Farm Details");

        spinner = (Spinner) findViewById(R.id.cropNameSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.crop_names, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), adapterView.getItemIdAtPosition(position) + ": selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            LatLng patagonia = new LatLng(mapDetails.getCenter().getLat(), mapDetails.getCenter().getLng());

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.styleUrl("mapbox://styles/mapbox/satellite-v9");
            options.camera(new CameraPosition.Builder()
                    .target(patagonia)
                    .zoom(mapDetails.getZoomLevel())
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

            // Add map fragment to parent container
            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mapboxMap.addPolygon(new PolygonOptions()
                        .addAll(drawPolygon())
                        .fillColor(Color.parseColor("#0d3bb2d0")));

            }
        });

    }

    private List<LatLng> drawPolygon() {
        List<LatLng> polygon = new ArrayList<>();
        for (Cordinates cordinate : mapDetails.getPolygon()) {
            polygon.add(new LatLng(cordinate.getLat(), cordinate.getLng()));
        }
        return polygon;

    }


}
