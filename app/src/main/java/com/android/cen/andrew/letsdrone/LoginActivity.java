package com.android.cen.andrew.letsdrone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_ACC = "acccounts";
    private static final String PREF_LOGIN = "login";
    private TextInputLayout mUsernameInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private TextInputEditText mUsernameEditText;
    private TextInputEditText mPasswordEditText;
    private MaterialButton mLoginButton;
    private MaterialButton mRegisterButton;
    private SharedPreferences mLoginPrefs;
    private SharedPreferences mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init member variables
        mUsernameInputLayout = findViewById(R.id.username_input_layout);
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordInputLayout = findViewById(R.id.password_input_layout);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.login_button);
        mRegisterButton = findViewById(R.id.register_button);
        mLoginPrefs = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        mAccounts = getSharedPreferences(PREF_ACC, MODE_PRIVATE);

        if (mLoginPrefs.getBoolean("doneLogin", false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        // login button listener
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin()) {
                    SharedPreferences.Editor editor = mLoginPrefs.edit();
                    editor.putBoolean("doneLogin", true);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateLogin() {
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mUsernameInputLayout.setError("");
        mPasswordInputLayout.setError("");

        if (mAccounts.getString(username, "nodata").equals(password)) {
            return true;
        }

        mUsernameInputLayout.setError(getResources().getString(R.string.error));
        mPasswordInputLayout.setError(getResources().getString(R.string.error));

        return false;
    }
}
