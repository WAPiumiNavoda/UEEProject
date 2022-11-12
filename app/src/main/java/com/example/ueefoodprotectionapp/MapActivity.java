package com.example.ueefoodprotectionapp;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import  com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//    }
//

    private GoogleMap mMap;
    ArrayList<LatLng>arrayList = new ArrayList<LatLng>();
    //    LatLng colombo = new LatLng(6.8333 , 80.0833);
    LatLng urbanSmoke = new LatLng(7.09048457,80.01999091);
    LatLng school = new LatLng(7.09013123,80.01401495);
    LatLng temple = new LatLng(7.09004505,80.01672398);
    LatLng oshibaHoldinh = new LatLng(7.08365219,80.01000638);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment.getMapAsync(this);
        arrayList.add(urbanSmoke);
        arrayList.add(school);
        arrayList.add(temple);
        arrayList.add(oshibaHoldinh);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(int i=0; i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("Maker"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }

//        mMap.addMarker(new MarkerOptions().position(colombo).title("You are currently here 1"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombo,(14.0f)));
    }
}