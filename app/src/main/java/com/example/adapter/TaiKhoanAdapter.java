package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Objects.TaiKhoan;
import com.example.QLDSV.R;

import java.util.ArrayList;

public class TaiKhoanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    ArrayList<TaiKhoan> listTaiKhoan;

    public TaiKhoanAdapter(Context context, int layout, ArrayList<TaiKhoan> listTaiKhoan) {
        this.context = context;
        this.layout = layout;
        this.listTaiKhoan = listTaiKhoan;
    }

    @Override
    public int getCount() {
        return listTaiKhoan.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder
    {
        TextView txtMaTK;
        TextView txtTenTK;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (v==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout,  null);

            viewHolder = new ViewHolder();


            // Ánh xạ view
            viewHolder.txtMaTK = (TextView) v.findViewById(R.id.txtMaTK);
            viewHolder.txtTenTK = (TextView) v.findViewById(R.id.txtTenTK);
            v.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) v.getTag();
        }


        // Gán giá trị
        TaiKhoan taikhoan = listTaiKhoan.get(i);
        viewHolder.txtMaTK.setText(taikhoan.getMatk());
        viewHolder.txtTenTK.setText(taikhoan.getTentk());
        return v;
    }
}
