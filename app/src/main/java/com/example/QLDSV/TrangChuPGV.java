package com.example.QLDSV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TrangChuPGV extends AppCompatActivity {
    Button btnMonhoc;

    Button btnLoptinchi;

    Button btnTaiKhoanSV;

    Button btnDangXuat, btnThongBao;

    String maGV = "";

    Button btnThemThongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu_pgv);

        Intent intent = getIntent();
        maGV = intent.getStringExtra("maGiangVien");

        btnMonhoc = findViewById(R.id.btnMonhoc);
        btnTaiKhoanSV = findViewById(R.id.btnTaiKhoanSV);
        btnDangXuat= findViewById(R.id.btnDangXuat);
        btnThongBao = findViewById(R.id.btnThongBao);
        btnMonhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Btn", "Bam nut ne");

                Intent intent = new Intent(TrangChuPGV.this, QLMonHoc.class);
                startActivity(intent);
                finish();
            }
        });
        btnTaiKhoanSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuPGV.this, QLTaiKhoanSV.class);
                startActivity(intent);
                finish();
            }
        });

        btnLoptinchi = findViewById(R.id.btnLoptinchi);
        btnLoptinchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuPGV.this, QLLopTinChi.class);
                startActivity(intent);
                finish();
            }
        });

        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuPGV.this, ThemThongBao.class);
                intent.putExtra("maGiangVien", maGV);
                intent.putExtra("vaiTro", "PGV");
                startActivity(intent);
                finish();
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuPGV.this, DangNhap.class);
                startActivity(intent);
            }});
    }
}