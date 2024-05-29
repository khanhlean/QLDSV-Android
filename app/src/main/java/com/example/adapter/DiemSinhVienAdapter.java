package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Objects.DiemSinhVien;
import com.example.QLDSV.R;

import java.util.ArrayList;


public class DiemSinhVienAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private LayoutInflater layoutInflater;
    ArrayList<DiemSinhVien> listDiemSV;
    private DiemSinhVien objectDiemSinhVien;

    public DiemSinhVienAdapter(Context context, ArrayList<DiemSinhVien> listDiemSV) {
        this.context = context;
        this.listDiemSV = listDiemSV;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DiemSinhVienAdapter(Context context, int layout, ArrayList<DiemSinhVien> listDiemSV) {
        this.context = context;
        this.layout=layout;
        this.listDiemSV=listDiemSV;
    }

    @Override
    public int getCount() {
        return listDiemSV.size();
    }

    @Override
    public Object getItem(int i) {
        return listDiemSV.get(i);
    }


    public long getItemId(int i) {
        return listDiemSV.get(i).getId();
    }


    private class ViewHolder
    {
        TextView maSV, diemCC, diemGK, diemCK, diemTK;
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
            viewHolder.maSV = (TextView) view.findViewById(R.id.maSV);
            viewHolder.diemCC = (TextView) view.findViewById(R.id.diemCC);
            viewHolder.diemGK = (TextView) view.findViewById(R.id.diemGK);
            viewHolder.diemCK = (TextView) view.findViewById(R.id.diemCK);
            viewHolder.diemTK = (TextView) view.findViewById(R.id.diemTK);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Gán giá trị
        objectDiemSinhVien =listDiemSV.get(i);
        viewHolder.maSV.setText(objectDiemSinhVien.getMaSV());
        viewHolder.diemCC.setText(objectDiemSinhVien.getDiemCC()+"");
        viewHolder.diemGK.setText(objectDiemSinhVien.getDiemGK()+"");
        viewHolder.diemCK.setText(objectDiemSinhVien.getDiemCK()+"");
        viewHolder.diemTK.setText(objectDiemSinhVien.getDiemTK()+"");

        // Đổi màu items
        view.setBackgroundResource(R.drawable.textlines);
        return view;
    }
}
