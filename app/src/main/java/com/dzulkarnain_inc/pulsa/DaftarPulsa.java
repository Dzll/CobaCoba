package com.dzulkarnain_inc.pulsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.dzulkarnain_inc.pulsa.ApiService;
import com.dzulkarnain_inc.pulsa.ListDataPulsa;
import com.dzulkarnain_inc.pulsa.ModelData;

public class DaftarPulsa extends AppCompatActivity{


    ArrayList<ModelData> datapulsa = new ArrayList<ModelData>();
    ListView listview;
    ListDataPulsa adapter;

    LinearLayout layout_loading;
    TextView text_load;
    ImageView icon_load;

    ProgressDialog loading;
    //EditText textnomer;
    TextView aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_pulsa);

        layout_loading = (LinearLayout) findViewById(R.id.layout_loading);

        text_load = (TextView) findViewById(R.id.text_load);
        icon_load = (ImageView) findViewById(R.id.icon_load);

        listview = (ListView) findViewById(R.id.listpulsa);
        listview.setDividerHeight(0);

        //textnomer = (EditText)findViewById(R.id.et_nomer);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigasi_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_pulsa:
                        break;
                    case R.id.menu_topup:
                        startActivity(new Intent(DaftarPulsa.this, SaldoActivity.class));
                        DaftarPulsa.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                        finish();
                        break;
                    case R.id.menu_user:
                        startActivity(new Intent(DaftarPulsa.this, UserActivity.class));
                        DaftarPulsa.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                        finish();
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

        loading = ProgressDialog.show(DaftarPulsa.this, null, "Please wait...", true, false);
        Call<List<ModelData>> call = service.getPulsaData();
        call.enqueue(new Callback<List<ModelData>>() {
            @Override
            public void onResponse(Call<List<ModelData>> call, Response<List<ModelData>> response) {

                // tambahkan
                datapulsa.clear();
                if (response.isSuccessful()) {
                    int jumlah = response.body().size();

                    for (int i = 0; i < jumlah; i++) {

                        ModelData data = new ModelData(
                                response.body().get(i).getIdPulsa(),
                                response.body().get(i).getJumlah_pulsa(),
                                response.body().get(i).getMasa_aktif(),
                                response.body().get(i).getHarga_pulsa());
                        datapulsa.add(data);
                        Log.d("RESPON", "onResponse: " + response.body().get(i).getIdPulsa());

                    }
                    listview.setVisibility(View.VISIBLE);
                    adapter = new ListDataPulsa(DaftarPulsa.this, R.layout.item_pulsa, datapulsa);
                    listview.setAdapter(adapter);

                    if (adapter.getCount() < 1 ) {
                        layout_loading.setVisibility(View.VISIBLE);
                        String error = "Daftar Kosong";
                        text_load.setText(error);
                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_data_kosong);
                        icon_load.setImageBitmap(icon);
                    } else {
                        layout_loading.setVisibility(View.GONE);
                    }
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ModelData>> call, Throwable t) {
                String error = "Error Retrive Data from Server!!!\n" + t.getMessage();
                text_load.setText(error);
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_network);
                icon_load.setImageBitmap(icon);
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
        datapulsa.clear();
        adapter = new ListDataPulsa(DaftarPulsa.this, R.layout.item_pulsa, datapulsa);
//        adapter.clear();
        listview.setAdapter(adapter);
        setup();
    }

}
