package com.dzulkarnain_inc.pulsa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PembayaranPulsa extends AppCompatActivity {

    String ID_PULSA,JUMLAH_PULSA, HARGA_PULSA, ID_USER_BELI, NAMA_USER_BELI;

    String kunci_uang, kunci_aidi;

    TextView harga_pl, jumlah_pl, beli_id, beli_nama;
    TextView uang_saldo;
    ProgressDialog loading;

    ImageView gam;

    String op;

    CardView mandiri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran_pulsa);

        getSupportActionBar().setTitle("Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Date currentTime = new java.sql.Date(System.currentTimeMillis());

        ID_PULSA = getIntent().getStringExtra(ModelData.idPulsa);
        JUMLAH_PULSA = getIntent().getStringExtra("jum");
        HARGA_PULSA = getIntent().getStringExtra("hrg");
        SharedPreferences sharedPreferences = getSharedPreferences("kirim_datanya", MODE_PRIVATE);
        ID_USER_BELI = sharedPreferences.getString("kirim_id", "");
        NAMA_USER_BELI = sharedPreferences.getString("kirim_nama", "");

        SharedPreferences shar = getSharedPreferences("uang_saldo", MODE_PRIVATE);
        kunci_uang = shar.getString("uang", "");
        kunci_aidi = shar.getString("uang_id", "");


        harga_pl = (TextView) findViewById(R.id.harga_pls);
        jumlah_pl = (TextView) findViewById(R.id.jml_pls);
        beli_id = (TextView)findViewById(R.id.beli_id);
        beli_nama = (TextView)findViewById(R.id.beli_nama);
        uang_saldo = (TextView)findViewById(R.id.bayar_topUp);

        harga_pl.setText(HARGA_PULSA);
        jumlah_pl.setText(JUMLAH_PULSA);
        beli_id.setText(ID_USER_BELI);
        beli_nama.setText(NAMA_USER_BELI);
        uang_saldo.setText(kunci_uang);

        final EditText pet_nomor = (EditText)findViewById(R.id.pet_nomor);
        pet_nomor.setFocusable(true);
        pet_nomor.setMaxLines(1);
        pet_nomor.setImeOptions(EditorInfo.IME_ACTION_DONE);

        gam = (ImageView)findViewById(R.id.logo_operator);

        Spinner spinner = (Spinner) findViewById(R.id.spin_operator);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operator_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                if (parent.getItemAtPosition(i).toString().equals("Indosat")){
                    gam.setBackgroundResource(R.mipmap.indosat);
                    op = "Indosat";
                }else if (parent.getItemAtPosition(i).toString().equals("Telkomsel")){
                    gam.setBackgroundResource(R.mipmap.telkomsel);
                    op = "Telkomsel";
                }else if (parent.getItemAtPosition(i).toString().equals("Smartfren")){
                    gam.setBackgroundResource(R.mipmap.smartfren);
                    op = "Smartfren";
                }else if (parent.getItemAtPosition(i).toString().equals("XL")){
                    gam.setBackgroundResource(R.mipmap.xl);
                    op = "XL";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        CardView crd_topup = (CardView)findViewById(R.id.byr_dgn_topup);
        crd_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pet_nomor.getText().toString().trim().equals("")){
                    pet_nomor.setError("Masukkan nomor !!");
                }else{

                    int hr = Integer.parseInt(harga_pl.getText().toString());
                    int sd = Integer.parseInt(uang_saldo.getText().toString());

                    if ( sd < hr ){
                        Toast.makeText(PembayaranPulsa.this, "Saldo PulsaPay tidak cukup!", Toast.LENGTH_LONG).show();
                    }else {

                        new AlertDialog.Builder(PembayaranPulsa.this)
                                .setTitle("Konfirmasi")
                                .setMessage("Ingin bayar cash ?")
                                .setIcon(R.mipmap.ic_action_info)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent intent = new Intent(PembayaranPulsa.this, NotaPulsa.class);
                                        intent.putExtra("telepon", pet_nomor.getText().toString());
                                        intent.putExtra("pulsa", jumlah_pl.getText().toString());
                                        intent.putExtra("total", harga_pl.getText().toString());

                                        loading = ProgressDialog.show(PembayaranPulsa.this, null, "Please wait...", true, false);
                                        tambahDataTransaksi(beli_id.getText().toString(), beli_nama.getText().toString(), pet_nomor.getText().toString(), ID_PULSA, jumlah_pl.getText().toString(), harga_pl.getText().toString(), op);

                                        startActivity(intent);
                                        finish();
                                    }
                                })

                                .setNegativeButton("Batal", null).show();
                    }
                }
            }
        });


        //bindData();

    }


    public void tambahDataTransaksi(String id_user, String nama_user, String telepon, String id_pulsa, String jumlah_pulsa, String harga_pulsa, String operator) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(new StringConverter())
                .build();
        ApiService service = retrofit.create(ApiService.class);

        // Update Nominal
        Call<ResponseBody> callup = service.updateNominal(kunci_aidi, kunci_uang,harga_pl.getText().toString().trim());
        callup.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(PembayaranPulsa.this, "Sisa saldo anda Rp " + (Integer.parseInt(kunci_uang) - Integer.parseInt(harga_pl.getText().toString().trim())), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


        // Transaksi
        Call<ResponseBody> call = service.transaksi_insert(id_user, nama_user, telepon, id_pulsa, jumlah_pulsa, harga_pulsa, operator);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(PembayaranPulsa.this, "Transaksi Berhasil", Toast.LENGTH_LONG).show();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(PembayaranPulsa.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
