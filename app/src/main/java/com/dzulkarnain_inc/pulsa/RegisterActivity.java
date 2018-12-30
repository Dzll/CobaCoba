package com.dzulkarnain_inc.pulsa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText nama, notelp, email, pass;
    Button reg;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getSupportActionBar().setTitle("Register");
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        nama = (EditText)findViewById(R.id.reg_nama);
        notelp = (EditText)findViewById(R.id.reg_telepon);
        email = (EditText)findViewById(R.id.reg_email);
        pass = (EditText)findViewById(R.id.reg_password);
        reg = (Button)findViewById(R.id.btn_register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a = nama.getText().toString();
                String b = notelp.getText().toString();
                String c = email.getText().toString();
                String d = pass.getText().toString();

                boolean ceknul = true;

                if (a.equals("")){
                    ceknul = true;
                    nama.setError("Nama kosong!!");
                }
                if (b.equals("")){
                    ceknul = true;
                    notelp.setError("Telepon kosong!!");
                }
                if (c.equals("")){
                    ceknul = true;
                    email.setError("Email kosong!!");
                }
                if (d.equals("")){
                    ceknul = true;
                    pass.setError("Password kosong!!");
                }

                if (!a.equals("") && !b.equals("") && !c.equals("") && !d.equals("")){
                    loading = ProgressDialog.show(RegisterActivity.this, null, "Please wait...", true, false);
                    tambahDataRegister(a,b,c,d);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    RegisterActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                    finish();
                }

            }
        });

        TextView tx_login = (TextView)findViewById(R.id.reg_tx_login);
        tx_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                RegisterActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                finish();
            }
        });

    }

    public void tambahDataRegister(String nama, String telp, String email, String passw) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(new StringConverter())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<ResponseBody> call = service.registerInsert(nama, telp, email, passw);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
