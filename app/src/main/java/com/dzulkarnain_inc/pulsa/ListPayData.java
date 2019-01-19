package com.dzulkarnain_inc.pulsa;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListPayData extends ArrayAdapter<ModelPayData> {

    private ArrayList<ModelPayData> list;
    private LayoutInflater inflater;
    private int res;
    private String no;

    public ListPayData(@NonNull Context context, int resource, ArrayList<ModelPayData> list) {
        super(context, resource, list);
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ListPayData.MyHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(res, parent, false);

            holder = new ListPayData.MyHolder();

            holder.TANGGAL = (TextView) convertView.findViewById(R.id.pay_tanggal);
            holder.ID_PAY = (TextView) convertView.findViewById(R.id.pay_code);
            holder.NOMINAL = (TextView) convertView.findViewById(R.id.pay_nominal);
            holder.STATUS = (TextView) convertView.findViewById(R.id.pay_status);

            convertView.setTag(holder);

        } else {

            holder = (ListPayData.MyHolder) convertView.getTag();
        }

        holder.TANGGAL.setText(list.get(position).getCreated_at());
        holder.ID_PAY.setText(list.get(position).getId_pay());
        holder.NOMINAL.setText(list.get(position).getNominal_pay());
        if (list.get(position).getStatus().equals("0")){
            holder.STATUS.setText("Menunggu Konfirmasi");
            holder.STATUS.setTextColor(Color.parseColor("#ef1515"));
        }else {
            holder.STATUS.setText("Success");
            holder.STATUS.setTextColor(Color.parseColor("#0dba32"));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void remove(ModelPayData object) {
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

        TextView TANGGAL;
        TextView ID_PAY;
        TextView NOMINAL;
        TextView STATUS;

    }


}
