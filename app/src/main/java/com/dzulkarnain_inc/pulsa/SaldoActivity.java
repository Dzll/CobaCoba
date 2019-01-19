package com.dzulkarnain_inc.pulsa;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaldoActivity extends AppCompatActivity {

    private Button topup;
    final Context context = this;

    ArrayList<ModelPayData> PayData = new ArrayList<ModelPayData>();
    ListPayData adapter;
    ListView listview;

    ProgressDialog loading;
    TextView cs_nom, cs_id;
    EditText userInput;

    String ID_USER_BELI, samb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        cs_nom = (TextView)findViewById(R.id.cash_nominal);
        cs_id = (TextView)findViewById(R.id.cash_id);

        getSupportActionBar().setTitle("PulsaPay");

        SharedPreferences sharedPreferences = getSharedPreferences("kirim_datanya", MODE_PRIVATE);
        ID_USER_BELI = sharedPreferences.getString("kirim_id", "");

        SharedPreferences sharedPref = getSharedPreferences("uang_saldo", MODE_PRIVATE);
        String u = sharedPref.getString("uang", "");
        String i = sharedPref.getString("uang_id", "");

        cs_nom.setText(u);
        cs_id.setText(i);

        topup = (Button)findViewById(R.id.btn_topup);

        listview = (ListView) findViewById(R.id.listPay);
        listview.setDividerHeight(0);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigasi_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_pulsa:
                        startActivity(new Intent(SaldoActivity.this, DaftarPulsa.class));
                        SaldoActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                        finish();
                        break;
                    case R.id.menu_topup:
                        break;
                    case R.id.menu_user:
                        startActivity(new Intent(SaldoActivity.this, UserActivity.class));
                        SaldoActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                        finish();
                        break;
                }
                return true;
            }
        });


        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                userInput = (EditText) promptsView.findViewById(R.id.tx_topup);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("TopUp",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        if (userInput.getText().toString().trim().equals("")){
                                            Toast.makeText(SaldoActivity.this, "Jumlah TopUp Kosong !", Toast.LENGTH_SHORT).show();
                                        }else {
                                            topuppulsapay(userInput.getText().toString().trim(), ID_USER_BELI);
                                            Intent intent = new Intent(SaldoActivity.this, ActivityBankBayar.class);
                                            intent.putExtra("sambutan", samb);
                                            intent.putExtra("nominal", userInput.getText().toString().trim());
                                            startActivity(intent);
                                        }
                                    }
                                })
                        .setNegativeButton("Batal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    public void topuppulsapay(String nominal,String id_user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Isi Topup
        loading = ProgressDialog.show(SaldoActivity.this, null, "Please wait...", true, false);
        ApiService service = retrofit.create(ApiService.class);
        Call<ResponseBody> call = service.topupPulsaPay(nominal, id_user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.dismiss();
                Toast.makeText(SaldoActivity.this, "Menunggu Konfirmasi !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        loading = ProgressDialog.show(SaldoActivity.this, null, "Please wait...", true, false);
        Call<List<ModelPayData>> call = service.getPayData(ID_USER_BELI);
        call.enqueue(new Callback<List<ModelPayData>>() {
            @Override
            public void onResponse(Call<List<ModelPayData>> call, Response<List<ModelPayData>> response) {

                PayData.clear();
                if (response.isSuccessful()) {
                    int jumlah = response.body().size();

                    for (int i = 0; i < jumlah; i++) {

                        ModelPayData data = new ModelPayData(
                                response.body().get(i).getId_pay(),
                                response.body().get(i).getNominal_pay(),
                                response.body().get(i).getCreated_at(),
                                response.body().get(i).getStatus()
                        );
                        PayData.add(data);
                        Log.d("RESPON", "onResponse: " + response.body().get(i).getId_pay());
                    }
                    listview.setVisibility(View.VISIBLE);
                    adapter = new ListPayData(SaldoActivity.this, R.layout.item_pulsapay, PayData);
                    listview.setAdapter(adapter);

                    loading.dismiss();
                }
//                Toast.makeText(UserActivity.this, "MasuQQ", Toast.LENGTH_LONG).show();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<ModelPayData>> call, Throwable t) {
                Toast.makeText(SaldoActivity.this, "Error Load History PulsaPay!", Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            adapter.clear();
            setup();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PayData.clear();
        adapter = new ListPayData(SaldoActivity.this, R.layout.item_pulsapay, PayData);
//        adapter.clear();
        listview.setAdapter(adapter);
        setup();
    }


}
