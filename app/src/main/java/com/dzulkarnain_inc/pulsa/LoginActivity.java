package com.dzulkarnain_inc.pulsa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    ArrayList<ModelDataUser> dataLoginUser = new ArrayList<ModelDataUser>();

    TextView reg;
    EditText log_email, log_password;
    Button btn_login;

    String em, pas;
    String EMAIL, PASSWORD;

    Context ctx = this;

    private CheckBox checkbox;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().setTitle("Login");
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Ngehapus dataPrefernece User ben mboiss
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();

        log_email = (EditText) findViewById(R.id.log_email);
        log_password = (EditText) findViewById(R.id.log_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = log_email.getText().toString().trim();
                String p = log_password.getText().toString().trim();

                if (e.equals("")) {
                    log_email.setError("Masukkan Email !!");
                }
                if (p.equals("")) {
                    log_password.setError("Masukkan Password !!");
                }

                if (!e.equals("") && !p.equals("")) {
                    LoginDataUser(log_email.getText().toString(), log_password.getText().toString());
                }

            }
        });

        reg = (TextView) findViewById(R.id.et_register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "Mboisss Register Click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                finish();
            }
        });

        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    log_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    log_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void LoginDataUser(final String email, final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        loading = ProgressDialog.show(LoginActivity.this, null, "Please wait...", true, false);
        Call<ModelDataUser> call = service.getLoginData(email, password);
        call.enqueue(new Callback<ModelDataUser>() {
            @Override
            public void onResponse(Call<ModelDataUser> call, Response<ModelDataUser> response) {

                ModelDataUser modus = response.body();
                assert modus != null;
                if (modus.getStatus() == 200)
                {
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    intent.putExtra("aidi_user", modus.getId_user());
                    intent.putExtra("nama_user", modus.getNama_user());
                    intent.putExtra("nomor_user", modus.getNotelp_user());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Selamat Datang, " + modus.getNama_user() + " !!", Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email / Password salah !!", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ModelDataUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error Retrive Data from Server!!!", Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });


    }


}
