package com.example.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Database.DatabaseManager;
import com.example.QLDSV.TrangChuSV;
import com.example.QLDSV.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChartFragment1 extends Fragment {
    private  int[] colors;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chart1, container, false);
        PieChart chart = (PieChart) rootView.findViewById(R.id.chart1);

        TextView tcdattv = (TextView) rootView.findViewById(R.id.tcdat_textview);
        TextView tcchuadattv = (TextView) rootView.findViewById(R.id.tcchuadat_textview);
        TextView tcchuahoctv = (TextView) rootView.findViewById(R.id.tcchuahoc_textview);

//        TrangChuSV ma = new TrangChuSV();
//        String maSinhVien = ma.getMaSinhVien();


        Intent intent = getActivity().getIntent();
        String maSinhVien = intent.getStringExtra("maSinhVien");

        Float phantramTCDat = null;
        Float phantramTCChuaDat = null;
        Float phantramTCChuaHoc = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql ="SELECT\n" +
                        "   (SUM(CASE WHEN dk.DiemCC*mh.HeSoCC + dk.DiemGK*mh.HeSoGK + dk.DiemCK*mh.HeSoCK >= 4 THEN mh.SoTinChi ELSE 0 END)) AS PhanTramTinChiDat,\n" +
                        "   (SUM(CASE WHEN dk.DiemCC*mh.HeSoCC + dk.DiemGK*mh.HeSoGK + dk.DiemCK*mh.HeSoCK <4 THEN mh.SoTinChi ELSE 0 END)) AS PhanTramTinChiChuaDat,\n" +
                        "   (200 - SUM(mh.SoTinChi)) AS PhanTramTinChiChuaHoc\n" +
                        "FROM\n" +
                        "        [dbo].[SinhVien] as sv\n" +
                        "        LEFT JOIN [dbo].[DangKi] as dk ON sv.MaSV = dk.MaSV\n" +
                        "        LEFT JOIN [dbo].[LopTinChi] as ltc ON dk.MaLTC =ltc.MaLTC\n" +
                        "        LEFT JOIN [dbo].[MonHoc] as mh ON ltc.MaMH = mh.MaMH\n" +
                        "WHERE\n" +
                        "        sv.MaSV = '"+maSinhVien+"'";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Float soTCDat = rs.getFloat("PhanTramTinChiDat");
                Float soTCChuaDat = rs.getFloat("PhanTramTinChiChuaDat");
                Float soTCChuaHoc = rs.getFloat("PhanTramTinChiChuaHoc");

                phantramTCDat = (soTCDat/200)*100;
                phantramTCChuaDat =(soTCChuaDat/200)*100;
                phantramTCChuaHoc = (soTCChuaHoc/200)*100;

                tcdattv.setText("Số tín chỉ đã đạt: "+soTCDat);
                tcchuadattv.setText("Số tín chỉ chưa đạt: "+soTCChuaDat);
                tcchuahoctv.setText("Số tín chỉ chưa học: "+soTCChuaHoc);
                tcdattv.setTextColor(Color.rgb(85, 255, 105));
                tcchuadattv.setTextColor(Color.rgb(255, 105, 85));
                tcchuahoctv.setTextColor(Color.rgb(180, 180, 180));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Thiết lập dữ liệu và thuộc tính biểu đồ

        List<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(phantramTCDat, "Đã tích lũy"));
//        entries.add(new PieEntry(phantramTCChuaDat, "Chưa tích lũy"));
//        entries.add(new PieEntry(phantramTCChuaHoc, "chưa học"));

        if (phantramTCDat != null && phantramTCDat != 0) {
            entries.add(new PieEntry(phantramTCDat, "Đã tích lũy"));
        }
        if (phantramTCChuaDat != null && phantramTCChuaDat != 0) {
            entries.add(new PieEntry(phantramTCChuaDat, "Chưa tích lũy"));
        }
        if (phantramTCChuaHoc != null && phantramTCChuaHoc != 0) {
            entries.add(new PieEntry(phantramTCChuaHoc, "Chưa học"));
        }

        if ( phantramTCChuaDat == 0) {
            colors = new int[]{Color.rgb(60, 179, 113), Color.rgb(180, 180, 180)};

        } else {
            colors = new int[]{Color.rgb(60, 179, 113), Color.rgb(255, 105, 85), Color.rgb(180, 180, 180)};

        }

        PieDataSet dataSet = new PieDataSet(entries, "Entries");


        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20);
        data.setValueFormatter(new PercentFormatter());
        chart.setData(data);
        chart.getDescription();


//        chart.setUsePercentValues(true);

        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(14);

        chart.notifyDataSetChanged();

        chart.setHoleRadius(60); // Đặt kích thước Hole
        chart.setTransparentCircleRadius(0); // Bỏ viền trắng ở ngoài
        chart.setDrawEntryLabels(false); // Tắt hiển thị label ở trung tâm
        chart.setCenterText("Tín chỉ"); // Đặt Center Text
        chart.setCenterTextSize(18); // Đặt kích thước Center Text
        chart.getDescription().setEnabled(false); // Tắt hiển thị Description
        chart.getLegend().setEnabled(false); // Tắt hiển thị Legend
        chart.animateY(1000, Easing.EasingOption.EaseInOutQuad); // Animation
        return rootView;
    }


}
