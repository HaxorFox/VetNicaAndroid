package com.hxrsoft.vetnica;


import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;






import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hxrsoft.vetnica.model.Local;


/**
 * A simple {@link Fragment} subclass.
 */
public class PruebaFragment extends Fragment implements OnMapReadyCallback  ,LocationListener,ActivityCompat.OnRequestPermissionsResultCallback  {
    private LocationManager lm;
    private Location location;
    private double longitude;
    private double latitude;

    private FirebaseDatabase database;
    private static final int REQUEST_PERMISSION = 1;
    private static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};

    GoogleMap map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_prueba, container, false);

        //SupportMapFragment mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.map1);
        //mapFragment.getMapAsync(this);




        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        database = FirebaseDatabase.getInstance();

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        map= googleMap;
        if (lm != null) {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
        }
        map.setTrafficEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 11));

        loadMarker();
    }
    @Override
    public void onLocationChanged(Location arg0) {

    }

    @Override
    public void onProviderDisabled(String arg0) {

    }

    @Override
    public void onProviderEnabled(String arg0) {

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }










    public void loadMarker(){
        DatabaseReference marcadores = database.getReference("marcadores");

        marcadores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                map.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshots) {
                    Local local = dataSnapshot1.getValue(Local.class);
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(local.getLatitude(), local.getLongitude()))
                            .title(local.getNombre())
                            .snippet(local.getDescripcion())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_veterinary)));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


}
