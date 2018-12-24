package com.dzulkarnain_inc.pulsa;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListDataTransaksi extends ArrayAdapter<ModelDataTransaksi> {

    private ArrayList<ModelDataTransaksi> list;
    private LayoutInflater inflater;
    private int res;
    private String no;

    public ListDataTransaksi(@NonNull Context context, int resource, ArrayList<ModelDataTransaksi> list) {
        super(context, resource, list);
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ListDataTransaksi.MyHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(res, parent, false);

            holder = new ListDataTransaksi.MyHolder();

            holder.KODE_TRANSAKSI = (TextView) convertView.findViewById(R.id.trans_kodetransaksi);
            holder.JUMLAH_PULSA = (TextView) convertView.findViewById(R.id.trans_jumlahpulsa);
            holder.NAMA_USER = (TextView) convertView.findViewById(R.id.trans_namauser);
            holder.TELEPON = (TextView) convertView.findViewById(R.id.trans_nomer);
            holder.HARGA = (TextView) convertView.findViewById(R.id.trans_hargapulsa);
            holder.TANGGAL = (TextView) convertView.findViewById(R.id.trans_tanggal);

            convertView.setTag(holder);

        } else {

            holder = (ListDataTransaksi.MyHolder) convertView.getTag();
        }

        holder.KODE_TRANSAKSI.setText(list.get(position).getId_transaksi());
        holder.JUMLAH_PULSA.setText(list.get(position).getJumlah_pulsa());
        holder.NAMA_USER.setText(list.get(position).getNama_user());
        holder.TELEPON.setText(list.get(position).getTelepon());
        holder.HARGA.setText("Rp "+list.get(position).getHarga_pulsa());
        holder.TANGGAL.setText(list.get(position).getTanggal());

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void remove(ModelDataTransaksi object) {
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

        TextView KODE_TRANSAKSI;
        TextView JUMLAH_PULSA;
        TextView NAMA_USER;
        TextView TELEPON;
        TextView HARGA;
        TextView TANGGAL;

    }
}
