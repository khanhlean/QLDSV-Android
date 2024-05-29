package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Objects.LopTinChiNhapDiem;
import com.example.QLDSV.R;

import java.util.ArrayList;


public class LopTinChiNhapDiemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private LayoutInflater layoutInflater;
    ArrayList<LopTinChiNhapDiem> listLTC;
    private LopTinChiNhapDiem objectLopTinChiNhapDiem;

    public LopTinChiNhapDiemAdapter(Context context, ArrayList<LopTinChiNhapDiem> listLTC) {
        this.context = context;
        this.listLTC = listLTC;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LopTinChiNhapDiemAdapter(Context context, int layout, ArrayList<LopTinChiNhapDiem> listLTC) {
        this.context = context;
        this.layout=layout;
        this.listLTC=listLTC;
    }

    @Override
    public int getCount() {
        return listLTC.size();
    }

    @Override
    public Object getItem(int i) {
        return listLTC.get(i);
    }


    public long getItemId(int i) {
        return listLTC.get(i).getId();
    }


    private class ViewHolder
    {
        TextView maLTC;
        TextView tenMH;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,  null);

            viewHolder = new ViewHolder();


            // Ánh xạ view
            viewHolder.maLTC = (TextView) view.findViewById(R.id.maLTC);
            viewHolder.tenMH = (TextView) view.findViewById(R.id.tenMH);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Gán giá trị
        objectLopTinChiNhapDiem =listLTC.get(i);
        viewHolder.maLTC.setText(objectLopTinChiNhapDiem.getMaLTC());
        viewHolder.tenMH.setText(objectLopTinChiNhapDiem.getTenMH());

        // Đổi màu items
        view.setBackgroundResource(R.drawable.textlines);
        return view;
    }
}
