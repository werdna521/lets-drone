package com.android.cen.andrew.letsdrone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ProfileFragment.Callbacks {
    private static final String PREF_LOGIN = "login";
    private static final String EXTRA_NAME = "nameeeee";
    private static final String EXTRA_USERNAME = "usernameeeee";
    private BottomNavigationView mBottomNavigationView;
    private String mName;
    private String mUsername;

    public static Intent newIntent(Context context, String name, String username) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }

    @Override
    public void onLogoutClicked() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("doneLogin", false);
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getUsername() {
        return  mUsername;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = getIntent().getStringExtra(EXTRA_NAME);
        mUsername = getIntent().getStringExtra(EXTRA_USERNAME);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        mBottomNavigationView = findViewById(R.id.bottom_nav_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.home_menu) {
                    fragment =  new HomeFragment();
                } else if (item.getItemId() == R.id.history_menu) {
                    fragment = new HistoryFragment();
                } else if (item.getItemId() == R.id.inbox_menu) {
                    fragment = new InboxFragment();
                } else if (item.getItemId() == R.id.profile_menu) {
                    fragment = new ProfileFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                return true;
            }
        });
    }
}
