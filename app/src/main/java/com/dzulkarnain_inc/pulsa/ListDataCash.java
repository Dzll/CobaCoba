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

public class ListDataCash extends ArrayAdapter<ModelDataCash> {

    private ArrayList<ModelDataCash> list;
    private LayoutInflater inflater;
    private int res;

    public ListDataCash(@NonNull Context context, int resource, ArrayList<ModelDataCash> list) {
        super(context, resource, list);
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ListDataCash.MyHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(res, parent, false);

            holder = new ListDataCash.MyHolder();

//            holder.ID_CASH = (TextView) convertView.findViewById(R.id.id_pulsa);
//            holder.NOMINAL = (TextView) convertView.findViewById(R.id.jumlah);
//            holder.ID_CASH = (TextView) convertView.findViewById(R.id.masa);

            convertView.setTag(holder);

        } else {

            holder = (ListDataCash.MyHolder) convertView.getTag();
        }

//        holder.ID_CASH.setText(list.get(position).getIdPulsa());
//        holder.NOMINAL.setText(list.get(position).getJumlah_pulsa());
//        holder.ID_CASH.setText(list.get(position).getMasa_aktif());

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void remove(ModelDataCash object) {
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

        TextView ID_CASH;
        TextView NOMINAL;
        TextView ID_USER;
    }
}
