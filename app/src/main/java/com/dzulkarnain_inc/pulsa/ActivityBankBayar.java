package com.dzulkarnain_inc.pulsa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityBankBayar extends AppCompatActivity {

    String nominal, get_nama;

    TextView tra_nama, tra_nominal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_bayar);

        getSupportActionBar().setTitle("Pembayaran 100% Secure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tra_nama = (TextView) findViewById(R.id.transfer_nama);
        tra_nominal = (TextView) findViewById(R.id.transfer_nominal);

        nominal = getIntent().getStringExtra("nominal");
        SharedPreferences sharedPreferences = getSharedPreferences("kirim_datanya", MODE_PRIVATE);
        get_nama = sharedPreferences.getString("kirim_nama", "");

        tra_nama.setText("Hai " + get_nama + ", \n Terimakasih telah membeli di PulsaKu.co.id. Mohon segera melakukan pembayaran.");
        tra_nominal.setText(nominal);

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
