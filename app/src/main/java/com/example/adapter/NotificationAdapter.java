package com.example.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.QLDSV.R;
import com.example.Objects.ThongBao;

import java.util.ArrayList;

import android.animation.ObjectAnimator;

import java.sql.Connection;
import com.example.Database.DatabaseManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Collections;
public class NotificationAdapter  extends BaseAdapter{

    private Context context;
    private int layout;
    ArrayList<ThongBao> listThongBao;
    private Comparator<ThongBao> comparator;

    public NotificationAdapter(Context context, int layout, ArrayList<ThongBao> listThongBao, Comparator<ThongBao> comparator) {
        this.context = context;
        this.layout = layout;
        this.listThongBao = listThongBao;
        this.comparator = comparator;
        Collections.sort(this.listThongBao, this.comparator);
    }

    @Override
    public int getCount() {
        return listThongBao.size();
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
        TextView txtTittle,txtDate,txtContent,txtNguoiGui;
        CardView cardView;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        ThongBao thongbao = listThongBao.get(i);


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            // Ánh xạ view
            viewHolder.cardView = view.findViewById(R.id.noti_cv);
            viewHolder.txtTittle = (TextView) view.findViewById(R.id.notification_title);
            viewHolder.txtDate = (TextView) view.findViewById(R.id.notification_date);
            viewHolder.txtContent = (TextView) view.findViewById(R.id.notification_content);
            viewHolder.txtNguoiGui = (TextView) view.findViewById(R.id.notification_nguoigui);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Gán giá trị
        if(!thongbao.isStatus())
        {
            viewHolder.cardView.setBackgroundColor(Color.rgb(135,206,235));
        }
        viewHolder.txtTittle.setText(thongbao.getTieude());
        viewHolder.txtDate.setText(thongbao.getNgaygio());
        viewHolder.txtContent.setText(thongbao.getNoidung());
        viewHolder.txtNguoiGui.setText(thongbao.getTennguoigui());

//         Xử lý sự kiện khi người dùng nhấn vào item
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int i=  thongbao.getMaTB();
                viewHolder.cardView.setBackgroundColor(Color.rgb(204,204,207));
                if(!thongbao.isStatus()){

                    try {
                    Connection conn = DatabaseManager.getConnection();
                    Statement stmt = conn.createStatement();
                    String sql = "UPDATE SinhVien_ThongBao SET DaDoc = 'true' WHERE matb = '"+i+"'";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                }
                if (viewHolder.txtContent.getVisibility() == View.GONE) {
                    viewHolder.txtContent.setVisibility(View.VISIBLE);
                    ObjectAnimator animation = ObjectAnimator.ofInt(viewHolder.txtContent, "height", 0, 200);
                    animation.setDuration(500);
                    animation.start();
                } else {
                    viewHolder.txtContent.setVisibility(View.GONE);
                }
            }
        });
        //Đổi màu items
//        view.setBackgroundResource(R.drawable.item_background);
        return view;
    }

    public static class ThongBaoComparator implements Comparator<ThongBao> {
        @Override
        public int compare(ThongBao thongBao1, ThongBao thongBao2) {
            if (thongBao1.isStatus() == thongBao2.isStatus()) {
                return thongBao1.getMaTB() - thongBao2.getMaTB();
            } else if (thongBao1.isStatus()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}

