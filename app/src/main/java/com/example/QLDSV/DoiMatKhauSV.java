package com.example.QLDSV;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DoiMatKhauSV extends AppCompatActivity {
    EditText txtMKCu, txtMKMoi, txtMKMoiXN;
    Button btnClickBack , btnDoiMatKhau;

    Connection conn;
    String maSinhVien, matKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhausv);

        Intent intent = getIntent();
        maSinhVien = intent.getStringExtra("maSinhVien");
        matKhau = intent.getStringExtra("matKhau");
        System.out.println("testMK" + matKhau);

        txtMKCu = findViewById(R.id.txtMKCu);
        txtMKMoi = findViewById(R.id.txtMKMoi);
        txtMKMoiXN = findViewById(R.id.txtMKMoiXN);

        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnClickBack = findViewById(R.id.btnClickBack);

        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoiMatKhauSV.this, ThongTinSinhVien.class);
                intent.putExtra("maSinhVien", maSinhVien);
                intent.putExtra("matKhau", matKhau);
                startActivity(intent);
                finish();
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty(txtMKCu)) {
                    txtMKCu.setError("Không được bỏ trống.");
                } else if (checkEmpty(txtMKMoi)) {
                    txtMKMoi.setError("Không được bỏ trống.");
                }else if (checkEmpty(txtMKMoiXN)) {
                    txtMKMoiXN.setError("Không được bỏ trống.");
                }else if(!txtMKMoi.getText().toString().equals(txtMKMoiXN.getText().toString())) {
                    txtMKMoiXN.setError("Mật khẩu xác nhận không hợp lệ.");
                }else if (!checkPassword(txtMKCu.getText().toString())){
                    alertFail();
                }
                else {
                    try {
                        conn = DatabaseManager.getConnection();
                        String query = "update TaiKhoan set  MatKhau = ? where Matk = '"+ maSinhVien +"' and  MatKhau = '" + matKhau + "'";
                        PreparedStatement pst = conn.prepareStatement(query);
                        pst.setString(1, txtMKMoi.getText().toString());
                        pst.executeUpdate();
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                    matKhau =  txtMKMoi.getText().toString();
                    alertSuccess();

                }
            }
        });
    }
    public boolean checkEmpty(EditText edt) {
        CharSequence check = edt.getText().toString();
        return TextUtils.isEmpty(check);
    }

    public boolean checkPassword(String mk){
        boolean check = matKhau.equals(mk) ? true : false;
        return check;
    }

    public void alertSuccess() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(DoiMatKhauSV.this);
        bulider.setMessage("Đổi mật khẩu thành công.");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(DoiMatKhauSV.this, ThongTinSinhVien.class);
                intent.putExtra("maSinhVien", maSinhVien);
                intent.putExtra("matKhau", matKhau);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
    public void alertFail() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(DoiMatKhauSV.this);
        bulider.setMessage("Mật khẩu không hợp lệ, vui lòng nhập lại");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
}
