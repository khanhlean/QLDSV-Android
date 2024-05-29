package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Objects.LopTinChi;
import com.example.QLDSV.R;

import java.util.ArrayList;

public class LopTinChiAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    ArrayList<LopTinChi> listLTC;

    public LopTinChiAdapter(Context context, int layout, ArrayList<LopTinChi> listLTC) {
        this.context = context;
        this.layout = layout;
        this.listLTC = listLTC;
    }

    @Override
    public int getCount() {
        return listLTC.size();
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
        TextView txtMaLTC;
        TextView txtNamhoc;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,  null);

            viewHolder = new ViewHolder();


            // Ánh xạ view
            viewHolder.txtMaLTC = (TextView) view.findViewById(R.id.txtMaLTC);
            viewHolder.txtNamhoc = (TextView) view.findViewById(R.id.txtNamhoc);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }


        // Gán giá trị
        LopTinChi ltc = listLTC.get(i);
        viewHolder.txtMaLTC.setText(ltc.getMaltc());
        viewHolder.txtNamhoc.setText(ltc.getNamhoc());
        return view;
    }
}
