package com.dzulkarnain_inc.pulsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

public class ListDataPulsa extends ArrayAdapter<ModelData> {

    private ArrayList<ModelData> list;
    private LayoutInflater inflater;
    private int res;

    public ListDataPulsa(@NonNull Context context, int resource, ArrayList<ModelData> list) {
        super(context, resource, list);
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        MyHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(res, parent, false);

            holder = new MyHolder();

            holder.ID = (TextView) convertView.findViewById(R.id.id_pulsa);
            holder.JUMLAH = (TextView) convertView.findViewById(R.id.jumlah);
            holder.MASA = (TextView) convertView.findViewById(R.id.masa);
            holder.HARGA = (TextView) convertView.findViewById(R.id.harga);
            holder.btn_bayar = (Button) convertView.findViewById(R.id.btn_bayar_pulsa);

            convertView.setTag(holder);

        } else {

            holder = (MyHolder) convertView.getTag();
        }

        holder.ID.setText(list.get(position).getIdPulsa());
        holder.JUMLAH.setText(list.get(position).getJumlah_pulsa());
        holder.MASA.setText(list.get(position).getMasa_aktif());
        holder.HARGA.setText("Rp "+list.get(position).getHarga_pulsa());
        holder.btn_bayar.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                final Context context = view.getContext();

                Intent intent = new Intent(context, PembayaranPulsa.class);
                intent.putExtra(ModelData.idPulsa, list.get(position).getIdPulsa());
                context.startActivity(intent);

            }
        }));

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void remove(ModelData object) {
        super.remove(object);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    static class MyHolder {

        TextView ID;
        TextView JUMLAH;
        TextView MASA;
        TextView HARGA;
        Button btn_bayar;

    }
}
