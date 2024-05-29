package com.example.QLDSV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.Database.DatabaseManager;
import com.example.Objects.SinhVien;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class ThongTinSinhVien extends AppCompatActivity {
    Connection conn;
    TextView txtTenSV, txtMSSV, txtGioiTinh, txtNgaySinh, txtDiaChi, txtNienKhoa;
     CardView cvHome;
     Button btnDMK, btnBack;
    String maSinhVien, matKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinsv);
        Intent intent = getIntent();
        maSinhVien = intent.getStringExtra("maSinhVien");
        matKhau = intent.getStringExtra("matKhau");
        System.out.println("aaaaa"+ maSinhVien);


        //      Ánh xạ
        setControl();

//      Click
        setEvent();
        loadThongTinSV();
    }

     void setEvent() {
        cvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinSinhVien.this, TrangChuSV.class);
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });
        btnDMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinSinhVien.this, DoiMatKhauSV.class);
                intent.putExtra("maSinhVien", maSinhVien);
                intent.putExtra("matKhau", matKhau);
                startActivity(intent);
            }
        });
         btnBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(ThongTinSinhVien.this, TrangChuSV.class);
                 intent.putExtra("maSinhVien", maSinhVien);
                 intent.putExtra("matKhau", matKhau);
                 startActivity(intent);
             }

         });
    }

     void setControl() {
        cvHome= findViewById(R.id.cv_home);
        btnDMK= findViewById(R.id.btn_dmk);
        btnBack= findViewById(R.id.btnBack);

        txtTenSV = (TextView)findViewById(R.id.txtTenSV);
        txtMSSV = (TextView)findViewById(R.id.txtMSSV);
        txtGioiTinh = (TextView)findViewById(R.id.txtGioiTinh);
        txtNgaySinh =(TextView) findViewById(R.id.txtNgaySinh);
        txtDiaChi = (TextView)findViewById(R.id.txtDiaChi);
        txtNienKhoa = (TextView)findViewById(R.id.txtNienKhoa);
    }

    public String convertDateToString (Date date){
         date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    public void loadThongTinSV (){
        SinhVien sv = new SinhVien();
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "select * from SinhVien where MaSV = '" + maSinhVien + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    txtMSSV.setText(rs.getString("MaSV"));
                    txtTenSV.setText(rs.getString("HoTen"));
                    boolean gioiTinh = Boolean.parseBoolean(rs.getString("Phai"));
                    System.out.println("GioiTinh" + gioiTinh);
                    txtGioiTinh.setText(gioiTinh==true ? "Nữ" : "Nam");
                    Date ngaySinh = rs.getDate("NgaySinh");
                    txtNgaySinh.setText(convertDateToString(ngaySinh));
                    txtDiaChi.setText(rs.getString("DiaChi"));
                    txtNienKhoa.setText(rs.getString("KhoaHoc"));

                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }


}
