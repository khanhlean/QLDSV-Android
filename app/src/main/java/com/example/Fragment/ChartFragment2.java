package com.example.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.Database.DatabaseManager;

import com.example.QLDSV.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
public class ChartFragment2 extends Fragment {

//    TrangChuSV ma = new TrangChuSV();
//    String maSinhVien = ma.getMaSinhVien();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chart2, container, false);
        LineChart chart = (LineChart) rootView.findViewById(R.id.chart2);

        Intent intent = getActivity().getIntent();
        String maSinhVien = intent.getStringExtra("maSinhVien");

        List<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        try {
            Connection conn = DatabaseManager.getConnection();
            String sql ="SELECT LopTinChi.NamHoc, LopTinChi.HocKi, AVG((DangKi.DiemCC * MonHoc.HeSoCC + DangKi.DiemGK * MonHoc.HeSoGK + DangKi.DiemCK * MonHoc.HeSoCK) / (MonHoc.HeSoCC + MonHoc.HeSoGK + MonHoc.HeSoCK)) AS DiemTBHK\n" +
                    "FROM DangKi\n" +
                    "INNER JOIN LopTinChi ON DangKi.MaLTC = LopTinChi.MaLTC\n" +
                    "INNER JOIN SinhVien ON DangKi.MaSV = SinhVien.MaSV\n" +
                    "INNER JOIN MonHoc ON LopTinChi.MaMH = MonHoc.MaMH\n" +
                    "WHERE SinhVien.MaSV = '"+maSinhVien+"'\n" +
                    "GROUP BY LopTinChi.NamHoc, LopTinChi.HocKi\n" +
                    "ORDER BY CAST(SUBSTRING(LopTinChi.NamHoc, 1, 4) AS INT), LopTinChi.HocKi";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            int i=0;
            while (rs.next()) {
                String namHoc = rs.getString("NamHoc");
                String namHocCut = namHoc.substring(0, 4);
                String hocKi = rs.getString("HocKi");
                labels.add(namHocCut+"-"+hocKi);
                float diemTBHK = rs.getFloat("DiemTBHK");
                entries.add(new Entry(i, diemTBHK));
                i++;
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

            chart.getXAxis().setLabelCount(labels.size());
            chart.getXAxis().setGranularity(1f);
            chart.getXAxis().setGranularityEnabled(true);
            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        if (entries.size() > 1) {

            // Tạo đường dữ liệu cho biểu đồ
            LineDataSet dataSet = new LineDataSet(entries, "Điểm trung bình học kì");
            dataSet.setColor(Color.BLUE);
            dataSet.setLineWidth(2f);
            dataSet.setCircleColor(Color.BLUE);
            dataSet.setCircleRadius(5f);
            dataSet.setDrawValues(true);
            dataSet.setValueTextSize(12f);

// Tạo đối tượng LineData và thêm đường dữ liệu vào
            LineData lineData = new LineData(dataSet);

// Thiết lập dữ liệu cho biểu đồ
            chart.setData(lineData);

// Thiết lập giới hạn cho trục y của biểu đồ
            chart.getAxisLeft().setAxisMinimum(0f);
            chart.getAxisLeft().setAxisMaximum(10f);

            chart.getDescription().setEnabled(false);
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            chart.getAxisRight().setEnabled(false);

            chart.setTouchEnabled(true);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);

            chart.animateX(1000);

// Cập nhật biểu đồ
            chart.invalidate();
        } else {
            chart.getXAxis().setValueFormatter(null);
        }
        return rootView;
    }
}
