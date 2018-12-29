package com.dzulkarnain_inc.pulsa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class PembayaranTopUp extends AppCompatActivity {

    String nominal, get_nama;
    TextView add_nom, topup_nama;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran_topup);

        getSupportActionBar().setTitle("Pembayaran 100% Secure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_nom = (TextView)findViewById(R.id.add_nominal);
        topup_nama = (TextView)findViewById(R.id.topup_nama);

        nominal = getIntent().getStringExtra("nominal");
        SharedPreferences sharedPreferences = getSharedPreferences("kirim_datanya", MODE_PRIVATE);
        get_nama = sharedPreferences.getString("kirim_nama", "");

        add_nom.setText(nominal);
        topup_nama.setText(get_nama);


    }

    // Animasi back header Transisi
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
