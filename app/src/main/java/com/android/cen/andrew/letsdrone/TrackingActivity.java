package com.android.cen.andrew.letsdrone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackingActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE };

    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private SupportMapFragment mapFragment = null;

    private TextInputEditText bookingcode;
    private MaterialButton track;
    static String blade = "odsakofkdsa";

    boolean okk;

    public static Intent newIntent(boolean ok, Context context) {
        Intent intent = new Intent(context, TrackingActivity.class);
        intent.putExtra(blade, ok);
        return intent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                initialize();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            checkPermissions();
        } catch (Exception e) {
            Log.d("okokok", e.getMessage());
        }

    }

    private SupportMapFragment getSupportMapFragment() {
        return (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
    }

    private void initialize() {
        setContentView(R.layout.activity_tracking);

        okk = getIntent().getBooleanExtra(blade, false);

        bookingcode = findViewById(R.id.booking_num);
        track = findViewById(R.id.track);

        if (okk) {
            bookingcode.setText("TESTCODE");
            map.setCenter(new GeoCoordinate( -6.1994, 106.781625),
                    Map.Animation.NONE);
            map.setZoomLevel(17);
            Image from = new Image();
            Image to = new Image();
            Image drone = new Image();
            try {
                from.setImageResource(R.drawable.marker_from1);
                to.setImageResource(R.drawable.marker_to1);
                drone.setImageResource(R.drawable.drone_small);

                MapMarker fromMarker =
                        new MapMarker(new GeoCoordinate(-6.201935, 106.781525), from);
                MapMarker toMarker =
                        new MapMarker(new GeoCoordinate(-6.1914, 106.7817), to);
                MapMarker droneMarker =
                        new MapMarker(new GeoCoordinate(-6.1994, 106.781625), drone);

                map.addMapObject(fromMarker);
                map.addMapObject(toMarker);
                map.addMapObject(droneMarker);
            } catch (Exception e) {
                Log.d("okokok", e.getMessage());
            }
        }

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookingcode.getText().toString().equals("TESTCODE")) {
                    map.setCenter(new GeoCoordinate( -6.1994, 106.781625),
                            Map.Animation.NONE);
                    map.setZoomLevel(17);
                    Image from = new Image();
                    Image to = new Image();
                    Image drone = new Image();
                    try {
                        from.setImageResource(R.drawable.marker_from1);
                        to.setImageResource(R.drawable.marker_to1);
                        drone.setImageResource(R.drawable.drone_small);

                        MapMarker fromMarker =
                                new MapMarker(new GeoCoordinate(-6.201935, 106.781525), from);
                        MapMarker toMarker =
                                new MapMarker(new GeoCoordinate(-6.1914, 106.7817), to);
                        MapMarker droneMarker =
                                new MapMarker(new GeoCoordinate(-6.1994, 106.781625), drone);

                        map.addMapObject(fromMarker);
                        map.addMapObject(toMarker);
                        map.addMapObject(droneMarker);
                    } catch (Exception e) {
                        Log.d("okokok", e.getMessage());
                    }
                } else {
                    
                }
            }
        });

        // Search for the map fragment to finish setup by calling init().
        mapFragment = getSupportMapFragment();
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    // retrieve a reference of the map from the map fragment
                    map = mapFragment.getMap();
                    // Set the map center to the Vancouver region (no animation)

                    // Set the zoom level to the average between min and max

                } else {
                    Log.e("okokok", "Cannot initialize SupportMapFragment (" + error + ")");
                }
            }
        });
    }

    private void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }
}
