package com.example.QLDSV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TrangChuGV extends AppCompatActivity {
    Button btnQlDiem, btnThongBao, btnDangXuat;

    Connection conn = null;

    public static String maGV = "";
    TextView txtTentk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giangvien);

        Intent intent = getIntent();
        maGV = intent.getStringExtra("maGiangVien");

        //      Ánh xạ
        setControl();

        //      Click
        setEvent();
        txtTentk.setText(loadThongTinGV(maGV));
    }

    private void setEvent() {
        btnQlDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuGV.this, NhapDiem.class);
                intent.putExtra("maGiangVien", maGV);
                startActivity(intent);
                finish();
            }
        });
        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuGV.this, ThemThongBao.class);
                intent.putExtra("maGiangVien", maGV);
                intent.putExtra("vaiTro", "GV");
                startActivity(intent);
                finish();
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuGV.this, DangNhap.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        btnQlDiem = findViewById(R.id.btnQLDiem);
        btnThongBao = findViewById(R.id.btnThongBao);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        txtTentk = findViewById(R.id.tentk);
    }
    public String loadThongTinGV(String matk) {
        String hoTen = "";
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM GiangVien where MAGV = '"+ matk + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    hoTen = rs.getString("HoTen");
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

        return "Xin chào " + hoTen;
    }
}