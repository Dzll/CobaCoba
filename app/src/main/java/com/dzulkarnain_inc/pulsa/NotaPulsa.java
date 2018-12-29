package com.dzulkarnain_inc.pulsa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class NotaPulsa extends AppCompatActivity {

    TextView tele, puls, tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nota_pulsa);

        getSupportActionBar().setTitle("Bukti Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String telepon = getIntent().getStringExtra("telepon");
        String pulsa = getIntent().getStringExtra("pulsa");
        String total = getIntent().getStringExtra("total");

        tele = (TextView)findViewById(R.id.nota_nomer);
        tele.setText(telepon);
        puls = (TextView)findViewById(R.id.nota_pulsa);
        puls.setText(pulsa);
        tot = (TextView)findViewById(R.id.nota_total);
        tot.setText(total);

    }

    // Animasi Transisi
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(NotaPulsa.this, UserActivity.class));
                NotaPulsa.this.overridePendingTransition(R.transition.none, R.transition.fade_out);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public void finish() {
//        super.finish();
//    }
}
