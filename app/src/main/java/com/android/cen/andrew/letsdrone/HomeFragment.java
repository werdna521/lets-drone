package com.android.cen.andrew.letsdrone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {
    private MaterialCardView mOrder;
    private MaterialCardView mTracking;
    private MaterialCardView mInfo;
    private MaterialCardView mPoin;
    private MaterialCardView mFirst;
    private MaterialCardView mSecond;
    private Callbacks mCallbacks;

    public interface Callbacks {
        public void start(Class cls);
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mOrder = view.findViewById(R.id.order);
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.start(OrderListActivity.class);
            }
        });

        mTracking = view.findViewById(R.id.tracking);
        mTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.start(TrackingActivity.class);
            }
        });

        mInfo = view.findViewById(R.id.info);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.start(InfoActivity.class);
            }
        });

        mFirst = view.findViewById(R.id.first);
        mFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WebActivity.newIntent("https://www.foldertekno.com/drone-terbaik/",
                        "10 Drone Terbaik",
                        getContext());
                startActivity(intent);
            }
        });

        mSecond = view.findViewById(R.id.second);
        mSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WebActivity.newIntent("https://dailysocial.id/post/kejutan-polaroid-luncurkan-beragam-jenis-drone-baru/",
                        "Drone Baru Polaroid",
                        getContext());
                startActivity(intent);
            }
        });

        mPoin = view.findViewById(R.id.poin);
        mPoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.start(PoinActivity.class);
            }
        });

        return view;
    }
}
