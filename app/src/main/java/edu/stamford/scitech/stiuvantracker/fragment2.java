package edu.stamford.scitech.stiuvantracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class fragment2 extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "Tracker - GMaps Share";
    private boolean mRequestingLocationUpdates = false;
    private MenuItem mShareButton;

    // Google API - Locations
    private GoogleApiClient mGoogleApiClient;

    // Google Maps
    private GoogleMap mGoogleMap;
    private PolylineOptions mPolylineOptions;
    private LatLng mLatLng;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment2.
     */
    public static fragment2 newInstance() {
        return new fragment2();
    }

    public fragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Toast.makeText(getActivity(), "It works.",
        // Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Hello world"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toast.makeText(getActivity(), "User Mode Enabled",
                Toast.LENGTH_SHORT).show();

        SupportMapFragment mf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.the_map);
        mf.getMapAsync(this);
    }
}

