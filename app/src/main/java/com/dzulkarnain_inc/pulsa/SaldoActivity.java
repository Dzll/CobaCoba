package com.dzulkarnain_inc.pulsa;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaldoActivity extends AppCompatActivity {

    private Button topup;
    final Context context = this;

    TextView id_data,nama_data;
    String jupuk_id_beli, jupuk_nama_beli;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        getSupportActionBar().setTitle("Saldo Cash");

        topup = (Button)findViewById(R.id.btn_topup);

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

                final EditText userInput = (EditText) promptsView.findViewById(R.id.tx_topup);

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
                                            Intent intent = new Intent(SaldoActivity.this, PembayaranTopUp.class);
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
}
