package com.example.QLDSV;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Database.DatabaseManager;
import com.example.Objects.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TaiKhoanSV_CT extends AppCompatActivity {
    Connection conn;
    String maTK;
    Button btnCapNhat, btnClickBack;
    TextView txtMaTK;
    EditText txtTenTK, txtMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_taikhoansv);
        Intent intent = getIntent();
        maTK = intent.getStringExtra("maTK");
        TaiKhoan tk = loadInfoTaiKhoan(maTK);

        txtMaTK = findViewById(R.id.txtMaTK);
        txtTenTK = findViewById(R.id.txtTenTK);
        txtMatKhau = findViewById(R.id.txtMatKhau);

        btnClickBack = findViewById(R.id.btnClickBack);
        btnCapNhat = findViewById(R.id.btnCapNhat);

        txtMaTK.setText(tk.getMatk());
        txtTenTK.setText(tk.getTentk());
        txtMatKhau.setText(tk.getMatkhau());

        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaiKhoanSV_CT.this, QLTaiKhoanSV.class);
                startActivity(intent);
                finish();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty(txtTenTK)) {
                    txtTenTK.setError("Tài khoản không được bỏ trống.");
                } else if (checkEmpty(txtMatKhau)) {
                    txtMatKhau.setError("Mật khẩu không được bỏ trống.");
                } else {
                    TaiKhoan tk = new TaiKhoan(txtMaTK.getText().toString(), txtTenTK.getText().toString(), txtMatKhau.getText().toString());
                    try {
                        conn = DatabaseManager.getConnection();
                        String query = "update TaiKhoan set TenTaiKhoan = ?, MatKhau = ? where MaTk = '" + tk.getMatk() + "'";
                        PreparedStatement pst = conn.prepareStatement(query);
                        pst.setString(1, tk.getTentk());
                        pst.setString(2, tk.getMatkhau());
                        pst.executeUpdate();
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                    alertSuccess();
                }
            }
        });

    }
    public TaiKhoan loadInfoTaiKhoan(String maTK) {
        TaiKhoan tk = null;
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "select * from TaiKhoan tk where tk.MaTk = '" + maTK + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String ma = rs.getString("MaTk");
                    String ten = rs.getString("TenTaiKhoan");
                    String matkhau = rs.getString("MatKhau");
                    tk = new TaiKhoan(ma, ten, matkhau);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return tk;
    }

    boolean checkEmpty(EditText edt) {
        CharSequence check = edt.getText().toString();
        return TextUtils.isEmpty(check);
    }
    public void alertSuccess() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(TaiKhoanSV_CT.this);
        bulider.setMessage("Cập nhật tài khoản thành công.");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(TaiKhoanSV_CT.this, QLTaiKhoanSV.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
}
