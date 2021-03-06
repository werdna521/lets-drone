package com.android.cen.andrew.letsdrone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
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
    private static final String EXTRA_FROM = "from";
    private static final String EXTRA_TO = "to";
    private static final String EXTRA_EX = "ex";
    private static final String EXTRA_DESC = "desc";
    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_W = "weight";
    private TextView too;
    private TextView fromm;
    private TextView exx;
    private TextView pricee;
    private MaterialButton ordernow;

    int REQUEST = 0;

    private String from, to, ex, desc, type;
    private int weight, price;

    public static Intent newIntent(String from, String to, String ex, String desc, String type, int weight, Context context) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(EXTRA_FROM, from);
        intent.putExtra(EXTRA_TO, to);
        intent.putExtra(EXTRA_EX, ex);
        intent.putExtra(EXTRA_DESC, desc);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_W, weight);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, null);
                finish();
            }
        }
    }

    private void initialize() {
        setContentView(R.layout.activity_order);
        fromm = findViewById(R.id.fromm);
        too = findViewById(R.id.too);
        exx = findViewById(R.id.ex);
        try {
            fromm.setText(getIntent().getStringExtra(EXTRA_FROM));
        } catch (Exception e) {
            Log.d("okokok", e.getMessage());
        }
        too.setText(getIntent().getStringExtra(EXTRA_TO));
        exx.setText(getIntent().getStringExtra(EXTRA_EX));

        from = getIntent().getStringExtra(EXTRA_FROM);
        to = getIntent().getStringExtra(EXTRA_TO);
        desc = getIntent().getStringExtra(EXTRA_DESC);
        type = getIntent().getStringExtra(EXTRA_TYPE);
        ex = getIntent().getStringExtra(EXTRA_EX);
        weight = getIntent().getIntExtra(EXTRA_W, 0);

        int k = 0;
        if (ex.equals("Let\'s express")) {
            k = 20000;
        } else {
            k = 13000;
        }

        price = k * weight;
        pricee = findViewById(R.id.price);
        int p = price / 1000;
        pricee.setText("Rp" + p + "K");

        ordernow = findViewById(R.id.order_button);
        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ConfirmActivity.newIntent(desc, type, weight, from, to, ex, price, OrderActivity.this);
                startActivityForResult(intent, REQUEST);
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
                    map.setCenter(new GeoCoordinate( -6.201935, 106.781525),
                            Map.Animation.NONE);
                    // Set the zoom level to the average between min and max
                    map.setZoomLevel(17);
                    Image from = new Image();
                    Image to = new Image();
                    try {
                        from.setImageResource(R.drawable.marker_from1);
                        to.setImageResource(R.drawable.marker_to1);

                    MapMarker fromMarker =
                            new MapMarker(new GeoCoordinate(-6.201935, 106.781525), from);
                    MapMarker toMarker =
                            new MapMarker(new GeoCoordinate(-6.1914, 106.7817), to);

                    map.addMapObject(fromMarker);
                    map.addMapObject(toMarker);
                    } catch (Exception e) {
                        Log.d("okokok", e.getMessage());
                    }
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
