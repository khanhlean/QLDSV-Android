package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Objects.LopTinChiDaDangKy;
import com.example.QLDSV.R;

import java.util.ArrayList;


public class LopTinChiDaDangKyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private LayoutInflater layoutInflater;
    ArrayList<LopTinChiDaDangKy> listLTCDDK;
    private LopTinChiDaDangKy objectLopTinChiDaDangKy;
    public static ArrayList<Integer> dsLTCduocchon = new ArrayList<Integer>();

    public LopTinChiDaDangKyAdapter(Context context, ArrayList<LopTinChiDaDangKy> listLTCDDK) {
        this.context = context;
        this.listLTCDDK = listLTCDDK;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LopTinChiDaDangKyAdapter(Context context,int layout, ArrayList<LopTinChiDaDangKy> listLTCDDK) {
        this.context = context;
        this.layout=layout;
        this.listLTCDDK=listLTCDDK;
    }

    @Override
    public int getCount() {
        return listLTCDDK.size();
    }

    @Override
    public Object getItem(int i) {
        return listLTCDDK.get(i);
    }


    public long getItemId(int i) {
        return listLTCDDK.get(i).getId();
    }


    private class ViewHolder
    {
        TextView maLTC;
        TextView tenMH;
        TextView nienKhoa;
        TextView hocKy;
        TextView ngayBD;
        TextView ngayKT;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

//        View rowView=view;
//        if (rowView==null) {
//            rowView=layoutInflater.inflate(R.layout.item_loptinchidadangky,parent,false);
//        }
//
//        // Ánh xạ view
//        TextView maLTC = (TextView) rowView.findViewById(R.id.textviewMa);
//        TextView tenMH = (TextView) rowView.findViewById(R.id.textviewTen);
//        TextView nienKhoa = (TextView) rowView.findViewById(R.id.textviewNienKhoa);
//        TextView hocKy = (TextView) rowView.findViewById(R.id.textviewHocKy);
//        TextView ngayBD = (TextView) rowView.findViewById(R.id.textviewNgay);
//        TextView ngayKT = (TextView) rowView.findViewById(R.id.textviewNgay2);

//        // Gán giá trị
//        objectLopTinChiDaDangKy =listLTCDDK.get(i);
//        maLTC.setText(objectLopTinChiDaDangKy.getMaLTC());
//        tenMH.setText(objectLopTinChiDaDangKy.getTenMH());
//        nienKhoa.setText(objectLopTinChiDaDangKy.getNienKhoa());
//        hocKy.setText(objectLopTinChiDaDangKy.getHocKy());
//        ngayBD.setText(objectLopTinChiDaDangKy.getNgayBD());
//        ngayKT.setText(objectLopTinChiDaDangKy.getNgayKT());
//
//        //Toast.makeText(context, "MonHoc da them: " + ThemChuyenNganh.arrMonHoc.size() + "\nDanh sach cac mon hoc: " + KeHoach.arrMonHoc.size(), Toast.LENGTH_SHORT).show();
//
//        // Đổi màu items
//        rowView.setBackgroundResource(R.drawable.item_dangky);
//        return rowView;



        ViewHolder viewHolder;
        if (view==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,  null);

            viewHolder = new ViewHolder();


            // Ánh xạ view
            viewHolder.maLTC = (TextView) view.findViewById(R.id.textviewMa);
            viewHolder.tenMH = (TextView) view.findViewById(R.id.textviewTen);
            viewHolder.nienKhoa = (TextView) view.findViewById(R.id.textviewNienKhoa);
            viewHolder.hocKy = (TextView) view.findViewById(R.id.textviewHocKy);
            viewHolder.ngayBD = (TextView) view.findViewById(R.id.textviewNgay);
            viewHolder.ngayKT = (TextView) view.findViewById(R.id.textviewNgay2);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Gán giá trị
        objectLopTinChiDaDangKy =listLTCDDK.get(i);
        viewHolder.maLTC.setText(objectLopTinChiDaDangKy.getMaLTC());
        viewHolder.tenMH.setText(objectLopTinChiDaDangKy.getTenMH());
        viewHolder.nienKhoa.setText(objectLopTinChiDaDangKy.getNienKhoa());
        viewHolder.hocKy.setText(objectLopTinChiDaDangKy.getHocKy());
        viewHolder.ngayBD.setText(objectLopTinChiDaDangKy.getNgayBD());
        viewHolder.ngayKT.setText(objectLopTinChiDaDangKy.getNgayKT());

        // Đổi màu items
        view.setBackgroundResource(R.drawable.item_dangky);
        return view;
    }
}
