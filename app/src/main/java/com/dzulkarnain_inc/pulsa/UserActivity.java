package com.dzulkarnain_inc.pulsa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    ArrayList<ModelDataTransaksi> dataTransaksi = new ArrayList<ModelDataTransaksi>();
    ArrayList<ModelPulsaPay> pulsaPay = new ArrayList<ModelPulsaPay>();

    ListView listview;
    ListDataTransaksi adapter;

    LinearLayout layout_loading;
    TextView text_load;
    ImageView icon_load;

    ProgressDialog loading;

    String AIDI_USER, NAMA_USER, NOMOR_USER;
    TextView aidi, nama, nomor;

    public static final String SHARED_PREF = "sharedPref";
    public static final String KUNCI = "kunci";
    public static final String KUNCI_nama = "kunci_nama";
    public static final String KUNCI_nomor = "kunci_nomor";
    public String kunci_id, kunci_nama, kunci_nomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        AIDI_USER = getIntent().getStringExtra("aidi_user");
        NAMA_USER = getIntent().getStringExtra("nama_user");
        NOMOR_USER = getIntent().getStringExtra("nomor_user");

        aidi = (TextView)findViewById(R.id.tx_id_user);
        nama = (TextView)findViewById(R.id.tx_nama_user);
        nomor = (TextView)findViewById(R.id.tx_nomor_user);

        LoadData();
        UpdateView();

        getSupportActionBar().setTitle("User Account");

        layout_loading = (LinearLayout) findViewById(R.id.layout_loading);

        text_load = (TextView) findViewById(R.id.text_load);
        icon_load = (ImageView) findViewById(R.id.icon_load);

        listview = (ListView) findViewById(R.id.listTransaksi);
        listview.setDividerHeight(0);

        final String a = aidi.getText().toString();
        final String b = nama.getText().toString();
        final String c = nomor.getText().toString();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigasi_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_pulsa:
                        saveData(a, b, c);
                        kirimData(a, b);
                        Intent intent = new Intent(UserActivity.this, DaftarPulsa.class);
                        startActivity(intent);
                        UserActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                        finish();
                        break;
                    case R.id.menu_topup:
                        saveData(a, b, c);
                        kirimData(a, b);
                        startActivity(new Intent(UserActivity.this, SaldoActivity.class));
                        UserActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                        finish();
                        break;
                    case R.id.menu_user:
                        break;
                }
                return true;
            }
        });

    }

    public void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<List<ModelPulsaPay>> calling = service.getPulsaPay(aidi.getText().toString().trim());
        calling.enqueue(new Callback<List<ModelPulsaPay>>() {
            @Override
            public void onResponse(Call<List<ModelPulsaPay>> call, Response<List<ModelPulsaPay>> response) {

                if (response.isSuccessful() && !response.body().isEmpty() ){
                    ModelPulsaPay pay = new ModelPulsaPay(
                            response.body().get(0).getNominal_pay(),
                            response.body().get(0).getId_pay()
                    );
                    pulsaPay.add(pay);

                    SharedPreferences sharedPreferences = getSharedPreferences("uang_saldo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("uang", response.body().get(0).getNominal_pay());
                    editor.putString("uang_id", response.body().get(0).getId_pay());
                    editor.apply();

                }


            }

            @Override
            public void onFailure(Call<List<ModelPulsaPay>> call, Throwable t) {
                Toast.makeText(UserActivity.this, "Error : Saldo Kosong Cuyyy", Toast.LENGTH_LONG).show();
            }
        });

        loading = ProgressDialog.show(UserActivity.this, null, "Please wait...", true, false);
        Call<List<ModelDataTransaksi>> call = service.getTransaksiData(aidi.getText().toString());
        call.enqueue(new Callback<List<ModelDataTransaksi>>() {
            @Override
            public void onResponse(Call<List<ModelDataTransaksi>> call, Response<List<ModelDataTransaksi>> response) {

                dataTransaksi.clear();
                if (response.isSuccessful()) {
                    int jumlah = response.body().size();

                    for (int i = 0; i < jumlah; i++) {

                        ModelDataTransaksi data = new ModelDataTransaksi(
                                response.body().get(i).getId_transaksi(),
                                response.body().get(i).getNohp_beli(),
                                response.body().get(i).getTanggal(),
                                response.body().get(i).getNama_user(),
                                response.body().get(i).getJumlah_pulsa(),
                                response.body().get(i).getHarga_pulsa(),
                                response.body().get(i).getOperator(),
                                response.body().get(i).getStatus()
                        );
                        dataTransaksi.add(data);
                        Log.d("RESPON", "onResponse: " + response.body().get(i).getId_transaksi());
                    }
                    listview.setVisibility(View.VISIBLE);
                    adapter = new ListDataTransaksi(UserActivity.this, R.layout.item_transaksi, dataTransaksi);
                    listview.setAdapter(adapter);

                    loading.dismiss();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<ModelDataTransaksi>> call, Throwable t) {
//                String error = "Error Retrive Data from Server!!!\n" + t.getMessage();
//                text_load.setText(error);
//                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_network);
//                icon_load.setImageBitmap(icon);
                Toast.makeText(UserActivity.this, "Error disini!!", Toast.LENGTH_LONG).show();
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
        dataTransaksi.clear();
        adapter = new ListDataTransaksi(UserActivity.this, R.layout.item_transaksi, dataTransaksi);
//        adapter.clear();
        listview.setAdapter(adapter);
        setup();
    }

    // Menu Navbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_logout:
                new AlertDialog.Builder(UserActivity.this)
                        .setTitle("Konfirmasi")
                        .setMessage("Ingin Keluar ?")
                        .setIcon(R.mipmap.ic_action_info)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                clearData();
                                Intent out = new Intent(UserActivity.this, LoginActivity.class);
                                startActivity(out);
                                Toast.makeText(UserActivity.this, "Log Out !!", Toast.LENGTH_SHORT).show();
                                finish();
                            }})

                        .setNegativeButton("Batal", null).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData(String id,String nam, String nom){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KUNCI, id);
        editor.putString(KUNCI_nama, nam);
        editor.putString(KUNCI_nomor, nom);
        editor.apply();

        //Toast.makeText(UserActivity.this, "Save Data!! -> "+id+" - "+nam+" - "+nom, Toast.LENGTH_LONG).show();
    }
    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        kunci_id = sharedPreferences.getString(KUNCI, "");
        kunci_nama = sharedPreferences.getString(KUNCI_nama, "");
        kunci_nomor = sharedPreferences.getString(KUNCI_nomor, "");
    }
    public void UpdateView(){
        if (!kunci_id.isEmpty()){

            aidi.setText(kunci_id);
            nama.setText(kunci_nama);
            nomor.setText(kunci_nomor);
            //Toast.makeText(UserActivity.this, "->>"+aidi.getText()+"<<-", Toast.LENGTH_LONG).show();

        }else {

            aidi.setText(AIDI_USER);
            nama.setText(NAMA_USER);
            nomor.setText(NOMOR_USER);
            //Toast.makeText(UserActivity.this, "->"+aidi.getText()+"<-", Toast.LENGTH_LONG).show();

        }
    }
    public void clearData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
        aidi.setText("Aidi");

        // Hapus NOminal Saldo
        SharedPreferences shar = getSharedPreferences("uang_saldo", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.clear().commit();

    }
    public void kirimData(String nama, String nomor){
        SharedPreferences sharedPreferences = getSharedPreferences("kirim_datanya", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("kirim_id", nama);
        editor.putString("kirim_nama", nomor);
        editor.apply();
    }


}
