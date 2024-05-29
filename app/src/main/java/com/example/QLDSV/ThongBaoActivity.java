package com.example.QLDSV;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;

import com.example.Database.DatabaseManager;
import com.example.Objects.ThongBao;
import com.example.adapter.NotificationAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.adapter.NotificationAdapter.ThongBaoComparator;

public class ThongBaoActivity extends AppCompatActivity {
    ListView listViewThongBao;
    ArrayList<ThongBao> arrThongBao;
    NotificationAdapter thongBaoAdapter;
    ImageButton btnBack;

    Button btnThoat;
    String maSinhVien;

    int vitri=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        Intent intent = getIntent();
        maSinhVien = intent.getStringExtra("maSinhVien");

        setControl();
        AddItem();
        setEnvent();

        ThongBaoComparator comparator = new ThongBaoComparator();
        thongBaoAdapter = new NotificationAdapter(this,R.layout.list_item_notification ,arrThongBao,comparator);
        listViewThongBao.setAdapter(thongBaoAdapter);



    }
    private void setControl() {
        listViewThongBao = (ListView) findViewById(R.id.listViewThongBao);
        btnBack= findViewById(R.id.btnBack);
        arrThongBao = new ArrayList<>();
    }
    private void setEnvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongBaoActivity.this, TrangChuSV.class );
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });
    }
    private void AddItem() {
        try {
            Intent intent = getIntent();
            maSinhVien = intent.getStringExtra("maSinhVien");

            Connection conn = DatabaseManager.getConnection();

            String sql = "SELECT tb.MaTB, tb.TieuDe, tb.NoiDung, tb.NgayGui, stb.DaDoc, gv.HoTen  \n" +
                    "FROM ThongBao tb \n" +
                    "JOIN GiangVien gv ON tb.MaGV = gv.MaGV \n" +
                    "JOIN SinhVien_ThongBao stb ON tb.MaTB = stb.MaTB \n" +
                    "WHERE stb.MaSV ='"+maSinhVien+"'";
            System.out.println(sql);
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("TieuDe"));
                Integer maTB = Integer.valueOf(rs.getString("maTB"));
                String tieuDe = rs.getString("TieuDe");
                String noiDung = rs.getString("NoiDung");
                String ngayGio = rs.getString("NgayGui");
                Boolean status= rs.getBoolean("DaDoc");
                String tenGiangVien= rs.getString("HoTen");
                arrThongBao.add(new ThongBao(maTB,tieuDe,noiDung,ngayGio,status,tenGiangVien));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}