package com.android.cen.andrew.letsdrone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREF_ACC = "acccounts";
    private TextInputLayout mNameInputLayout;
    private TextInputLayout mUsernameInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mPasswordRepeatInputLayout;
    private TextInputEditText mNameEditText;
    private TextInputEditText mUsernameEditText;
    private TextInputEditText mPasswordEditText;
    private TextInputEditText mPasswordRepeatEditText;
    private MaterialButton mRegisterButton;
    private SharedPreferences mAccounts;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init variables
        mNameInputLayout = findViewById(R.id.name_input_layout);
        mNameEditText = findViewById(R.id.name_edit_text);
        mUsernameInputLayout = findViewById(R.id.username_input_layout);
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordInputLayout = findViewById(R.id.password_input_layout);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mPasswordRepeatInputLayout = findViewById(R.id.password_repeat_input_layout);
        mPasswordRepeatEditText = findViewById(R.id.password_repeat_edit_text);
        mRegisterButton = findViewById(R.id.register_button);
        mAccounts = getSharedPreferences(PREF_ACC, MODE_PRIVATE);
        mToolbar = findViewById(R.id.toolbar);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateRegister()) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateRegister() {
        String name = mNameEditText.getText().toString();
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String passwordRepeat = mPasswordRepeatEditText.getText().toString();
        boolean flag = false;

        mNameInputLayout.setError("");
        mUsernameInputLayout.setError("");
        mPasswordInputLayout.setError("");
        mPasswordRepeatInputLayout.setError("");

        if (name.isEmpty()) {
            mNameInputLayout.setError("Field can't be empty");
            flag = true;
        }

        if (username.isEmpty()) {
            mUsernameInputLayout.setError("Field can't be empty");
            flag = true;
        }

        if (password.isEmpty()) {
            mPasswordInputLayout.setError("Field can't be empty");
            flag = true;
        }

        if (passwordRepeat.isEmpty()) {
            mPasswordRepeatInputLayout.setError("Field can't be empty");
            flag = true;
        }

        if (flag) {
            return false;
        }

        if (username.contains(" ")) {
            mUsernameInputLayout.setError("Username shouldn't contain any white spaces");
            return false;
        }

        if (username.length() < 8) {
            mUsernameInputLayout.setError("Username should be at least 8 characters");
            return false;
        }

        if (password.length() < 8) {
            mPasswordInputLayout.setError("Password should be at least 8 characters");
            return false;
        }

        if (!mAccounts.getString(username, "nodata").equals("nodata")) {
            mUsernameInputLayout.setError("Username not available");
            return false;
        }

        if (!password.equals(passwordRepeat)) {
            mPasswordInputLayout.setError("Password mismatch");
            mPasswordRepeatInputLayout.setError("Password mismatch");
            return false;
        }

        SharedPreferences.Editor editor = mAccounts.edit();
        editor.putString(username, password);
        editor.putString(username + " fullname", name);
        editor.apply();

        return true;
    }
}
