package com.android.cen.andrew.letsdrone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class OrderListActivity extends AppCompatActivity {
    private TextInputEditText desc;
    private TextInputEditText type;
    private TextInputEditText weight;
    private TextInputEditText from;
    private TextInputEditText to;
    private MaterialButton express;
    private MaterialButton regular;
    private  static final int REQUEST_EXIT = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, null);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        desc = findViewById(R.id.desc);
        type = findViewById(R.id.type);
        weight = findViewById(R.id.weight);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        express = findViewById(R.id.express_button);
        regular = findViewById(R.id.regular_button);

        express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OrderActivity.newIntent(from.getText().toString(), to.getText().toString(), "Let\'s express", desc.getText().toString(), type.getText().toString(), Integer.parseInt(weight.getText().toString()), OrderListActivity.this);
                startActivityForResult(intent, REQUEST_EXIT);
            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OrderActivity.newIntent(from.getText().toString(), to.getText().toString(), "Let\'s regular", desc.getText().toString(), type.getText().toString(), Integer.parseInt(weight.getText().toString()), OrderListActivity.this);
                startActivityForResult(intent, REQUEST_EXIT);
            }
        });
    }
}
