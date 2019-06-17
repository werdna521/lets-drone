package com.android.cen.andrew.letsdrone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class ProfileFragment extends Fragment {
    private MaterialCardView mLogout;
    private Callbacks mCallbacks;
    private TextView mFullname;
    private TextView mUsername;

    public interface Callbacks {
        public void onLogoutClicked();
        public String getName();
        public String getUsername();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false) ;

        mLogout = view.findViewById(R.id.logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onLogoutClicked();
            }
        });

        mFullname = view.findViewById(R.id.fullname);
        mFullname.setText(mCallbacks.getName());

        String username = "(" + mCallbacks.getUsername() + ")";
        mUsername = view.findViewById(R.id.username);
        mUsername.setText(username);

        return view;
    }
}
