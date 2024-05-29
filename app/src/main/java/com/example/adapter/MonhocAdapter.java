package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Objects.MonHoc;
import com.example.QLDSV.R;

import java.util.ArrayList;

public class MonhocAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    ArrayList<MonHoc> listMonhoc;

    public MonhocAdapter(Context context, int layout, ArrayList<MonHoc> listMonhoc) {
        this.context = context;
        this.layout = layout;
        this.listMonhoc = listMonhoc;
    }

    @Override
    public int getCount() {
        return listMonhoc.size();
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
        TextView txtMaMH;
        TextView txtTenMH;
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
            viewHolder.txtMaMH = (TextView) view.findViewById(R.id.txtMaMH);
            viewHolder.txtTenMH = (TextView) view.findViewById(R.id.txtTenMH);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }


        // Gán giá trị
        MonHoc monhoc = listMonhoc.get(i);
        viewHolder.txtMaMH.setText(monhoc.getMamh());
        viewHolder.txtTenMH.setText(monhoc.getTenmh());
        return view;
    }
}

