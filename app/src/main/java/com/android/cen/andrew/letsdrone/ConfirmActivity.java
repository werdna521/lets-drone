package com.android.cen.andrew.letsdrone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class ConfirmActivity extends AppCompatActivity {
    private static final String EXTRA_DESC = "desccccc";
    private static final String EXTRA_type = "typeeee";
    private static final String EXTRA_weight = "wwwwwwwwwwww";
    private static final String EXTRA_from = "frommmmmmmmm";
    private static final String EXTRA_to = "toooooooooo";
    private static final String EXTRA_ex = "exxxxxxx";
    private static final String EXTRA_price = "priceeeeeee";
    private String from, to, ex, desc, type;
    private int weight, price;
    private TextView descc;
    private TextView typee;
    private TextView weightt;
    private TextView fromm;
    private TextView too;
    private TextView exx;
    private TextView pricee;
    private MaterialButton button;

    public static Intent newIntent(String desc, String type, int weight, String from, String to,
                                   String ex, int price, Context context) {
        Intent intent = new Intent(context, ConfirmActivity.class);
        intent.putExtra(EXTRA_DESC, desc);
        intent.putExtra(EXTRA_type, type);
        intent.putExtra(EXTRA_weight, weight);
        intent.putExtra(EXTRA_from, from);
        intent.putExtra(EXTRA_to, to);
        intent.putExtra(EXTRA_ex, ex);
        intent.putExtra(EXTRA_price, price);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        from = getIntent().getStringExtra(EXTRA_from);
        to = getIntent().getStringExtra(EXTRA_to);
        desc = getIntent().getStringExtra(EXTRA_DESC);
        type = getIntent().getStringExtra(EXTRA_type);
        ex = getIntent().getStringExtra(EXTRA_ex);
        weight = getIntent().getIntExtra(EXTRA_weight, 0);
        price = getIntent().getIntExtra(EXTRA_price, 0);

        fromm = findViewById(R.id.from);
        too = findViewById(R.id.to);
        descc = findViewById(R.id.desc);
        typee = findViewById(R.id.type);
        exx = findViewById(R.id.ex);
        weightt =findViewById(R.id.weight);
        pricee = findViewById(R.id.price);

        fromm.setText("From: " + from);
        too.setText("To: " + to);
        descc.setText("Desc: " + desc);
        typee.setText("Type: " + type);
        exx.setText("Using: " + ex);
        weightt.setText("Weight: " + weight);
        pricee.setText("Price: Rp" + price);

        button = findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });
    }
}
