package edu.stamford.scitech.stiuvantracker;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class fragment2 extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "Tracker - GMaps Follow";
    private static final String PUBNUB_TAG = "PUBNUB";
    private boolean isFirstMessage = true;
    private boolean mRequestingLocationUpdates = false;
    private Button viewButton;
    private String buttonstate = "stop";
    private String ret = "";

    // Google Maps
    private GoogleMap mGoogleMap;
    //	private Polyline mPolyline;
    private PolylineOptions mPolylineOptions;
    private Marker mMarker;
    private MarkerOptions mMarkerOptions;
    private LatLng mLatLng;

    // PubNub
    private Pubnub mPubnub;
    private String channelName;
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
        //map.addMarker(new MarkerOptions()
                //.position(new LatLng(10, 10))
                //.title("Hello world"));

        mGoogleMap = map;
        mGoogleMap.setMyLocationEnabled(true);
        Log.d(TAG, "Map Ready");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toast.makeText(getActivity(), "User Mode Enabled",
                Toast.LENGTH_LONG).show();

        try {
            InputStream inputStream = getActivity().openFileInput("mytextfile.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                //Toast.makeText(getActivity(), ret,
                  //      Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        //SupportMapFragment mf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.the_map);
        //mf.getMapAsync(this);

        // Get Channel Name
        //Intent intent = getActivity().getIntent();
        //channelName = intent.getExtras().getString("channel");
        channelName = ret;
        Log.d(TAG, "Passed Channel Name: " + channelName);
        //Toast.makeText(getActivity(), channelName,
          //      Toast.LENGTH_LONG).show();

        SupportMapFragment mf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.the_map);
        mf.getMapAsync(this);

        viewButton = (Button) getActivity().findViewById(R.id.button3);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequestingLocationUpdates = !mRequestingLocationUpdates;
                if (buttonstate == "stop" && mRequestingLocationUpdates) {
                    buttonstate = "start";
                    viewButton.setText("Stop View");
                    startFollowingLocation();
                } else if (buttonstate == "start" && !mRequestingLocationUpdates) {
                    buttonstate = "stop";
                    viewButton.setText("View Location");
                    stopFollowingLocation();

                }
            }
        });

    }

    private void startFollowingLocation() {
        initializePolyline();
        mPubnub = PubNub.startPubnub();
        PubNub.subscribe(mPubnub, channelName, subscribeCallback);
    }

    private void stopFollowingLocation() {
        mPubnub.unsubscribe(channelName);
        isFirstMessage = true;
    }


    // =========================================================================
    // Map Editing Methods
    // =========================================================================

    private void initializePolyline() {
        mGoogleMap.clear();
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(Color.BLUE).width(10);
        mGoogleMap.addPolyline(mPolylineOptions);

        mMarkerOptions = new MarkerOptions();
    }

    private void updatePolyline() {
        mPolylineOptions.add(mLatLng);
        mGoogleMap.clear();
        mGoogleMap.addPolyline(mPolylineOptions);
    }

    private void updateCamera() {
        mGoogleMap
                .animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
    }

    private void updateMarker() {
//		if (!isFirstMessage) {
//			isFirstMessage = false;
//			mMarker.remove();
//		}
        mMarker = mGoogleMap.addMarker(mMarkerOptions.position(mLatLng));
    }

    private void updateSeat(){

    }

    // =========================================================================
    // PubNub Callback
    // =========================================================================

    Callback subscribeCallback = new Callback() {

        @Override
        public void successCallback(String channel, Object message) {
            Log.d(PUBNUB_TAG, "Message Received: " + message.toString());
            JSONObject jsonMessage = (JSONObject) message;
            try {
                double mLat = jsonMessage.getDouble("lat");
                double mLng = jsonMessage.getDouble("lng");
                mLatLng = new LatLng(mLat, mLng);
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updatePolyline();
                    updateCamera();
                    updateMarker();
                    //updateSeat();
                }
            });
        }
    };

}

