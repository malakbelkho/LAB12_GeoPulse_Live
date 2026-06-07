package com.malak.geopulselive;


import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class LiveMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap liveGoogleMap;
    private RequestQueue mapQueue;

    private final String positionsEndpoint =
            "http://10.0.2.2/showPositions.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_map);

        mapQueue = Volley.newRequestQueue(getApplicationContext());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.liveMap);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        liveGoogleMap = googleMap;
        loadSavedPositions();
    }

    private void loadSavedPositions() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                positionsEndpoint,
                null,
                response -> {
                    try {
                        JSONArray positions = response.getJSONArray("positions");

                        if (positions.length() == 0) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Aucune position trouvée",
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }

                        LatLng firstPoint = null;

                        for (int index = 0; index < positions.length(); index++) {
                            JSONObject item = positions.getJSONObject(index);

                            double latitude = item.getDouble("latitude");
                            double longitude = item.getDouble("longitude");
                            String date = item.getString("date");
                            String device = item.getString("imei");

                            LatLng point = new LatLng(latitude, longitude);

                            if (firstPoint == null) {
                                firstPoint = point;
                            }

                            liveGoogleMap.addMarker(
                                    new MarkerOptions()
                                            .position(point)
                                            .title("Position GPS")
                                            .snippet("Date : " + date + " | Appareil : " + device)
                                            .icon(BitmapDescriptorFactory.defaultMarker(
                                                    BitmapDescriptorFactory.HUE_VIOLET
                                            ))
                            );
                        }

                        if (firstPoint != null) {
                            liveGoogleMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(firstPoint, 15f)
                            );
                        }

                    } catch (Exception e) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Erreur JSON : " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                },
                error -> Toast.makeText(
                        getApplicationContext(),
                        "Erreur chargement carte : " + error.toString(),
                        Toast.LENGTH_LONG
                ).show()
        );

        mapQueue.add(request);
    }
}