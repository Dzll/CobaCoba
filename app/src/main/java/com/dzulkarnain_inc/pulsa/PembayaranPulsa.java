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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

    TextView harga_pl, jumlah_pl, beli_id, beli_nama;
    ProgressDialog loading;

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

        harga_pl = (TextView) findViewById(R.id.harga_pls);
        jumlah_pl = (TextView) findViewById(R.id.jml_pls);
        beli_id = (TextView)findViewById(R.id.beli_id);
        beli_nama = (TextView)findViewById(R.id.beli_nama);

        harga_pl.setText(HARGA_PULSA);
        jumlah_pl.setText(JUMLAH_PULSA);
        beli_id.setText(ID_USER_BELI);
        beli_nama.setText(NAMA_USER_BELI);


        final TextView ptx_nomor = (TextView)findViewById(R.id.ptx_nomor);
        final EditText pet_nomor = (EditText)findViewById(R.id.pet_nomor);
        final Button oke = (Button)findViewById(R.id.oke);
        pet_nomor.setFocusable(true);
        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pet_nomor.getText().toString().trim().equals("")){
                    pet_nomor.setError("Masukkan nomor !!");
                }else {
                    ptx_nomor.setText(pet_nomor.getText().toString().trim());
                    pet_nomor.setVisibility(View.GONE);
                    ptx_nomor.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    oke.setVisibility(View.GONE);
                }

            }
        });

        CardView crd_topup = (CardView)findViewById(R.id.byr_dgn_topup);
        crd_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pet_nomor.getText().toString().trim().equals("")){
                    pet_nomor.setError("Masukkan nomor !!");
                }else{
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
                                    tambahDataTransaksi(beli_id.getText().toString(), beli_nama.getText().toString(), pet_nomor.getText().toString(), ID_PULSA, jumlah_pl.getText().toString(), harga_pl.getText().toString());

                                    startActivity(intent);
                                    finish();
                                }})

                            .setNegativeButton("Batal", null).show();
                }
            }
        });


        //bindData();

    }

    public void tambahDataTransaksi(String id_user, String nama_user, String telepon, String id_pulsa, String jumlah_pulsa, String harga_pulsa) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(new StringConverter())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<ResponseBody> call = service.transaksi_insert(id_user, nama_user, telepon, id_pulsa, jumlah_pulsa, harga_pulsa);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                BufferedReader reader = null;
//
//                String respon = "";
//
//                try {
//                    reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
//                    respon = reader.readLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                Toast.makeText(PembayaranPulsa.this, "Transaksi Berhasil", Toast.LENGTH_SHORT).show();
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
