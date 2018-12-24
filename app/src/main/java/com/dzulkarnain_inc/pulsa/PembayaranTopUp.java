package com.dzulkarnain_inc.pulsa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PembayaranTopUp extends AppCompatActivity {

    String nominal;
    TextView add_nom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran_topup);

        getSupportActionBar().setTitle("Pembayaran 100% Secure");

        add_nom = (TextView)findViewById(R.id.add_nominal);

        nominal = getIntent().getStringExtra("nominal");
        add_nom.setText(nominal);


    }
}
