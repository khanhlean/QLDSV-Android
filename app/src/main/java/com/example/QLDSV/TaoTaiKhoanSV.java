package com.example.QLDSV;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Database.DatabaseManager;
import com.example.Objects.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TaoTaiKhoanSV extends AppCompatActivity {
    EditText txtTenTK, txtMatKhau;
    Button btnClickBack, btnThem;
    Connection conn;
    Spinner spinnerMaSV;
    ArrayList<String> listSpinnerMaSV = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taotaikhoansv);

        spinnerMaSV = (Spinner)findViewById(R.id.spinnerMaSV) ;
        btnClickBack = findViewById(R.id.btnClickBack);
        btnThem = findViewById(R.id.btnThem);
        txtTenTK = findViewById(R.id.txtTenTK);
        txtMatKhau = findViewById(R.id.txtMatKhau);

        loadSpinnerMSSV(spinnerMaSV, listSpinnerMaSV);

        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaoTaiKhoanSV.this, QLTaiKhoanSV.class);
                startActivity(intent);
            }

        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maSV = spinnerMaSV.getSelectedItem().toString();
                if(maSV.equals("Chọn mã sinh viên")){
                    AlertDialog.Builder bulider = new AlertDialog.Builder(TaoTaiKhoanSV.this);
                    bulider.setMessage("Vui lòng chọn mã sinh viên.");
                    bulider.setCancelable(true);
                    bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = bulider.create();
                    alert.show();
                }
                else if(checkEmpty(txtTenTK)) {
                    txtTenTK.setError("Vui lòng nhập tên tài khoản.");
                }
                else if(checkEmpty(txtMatKhau)) {
                    txtMatKhau.setError("Vui lòng nhập mật khẩu.");
                }
                else {
                    TaiKhoan tk = new TaiKhoan(maSV,txtTenTK.getText().toString().trim(), txtMatKhau.getText().toString().trim());
                    insertData(tk);
                    alertSuccess();

                }
            }
        });
    }

    public void loadSpinnerMSSV (Spinner spinner, ArrayList listSpinnerMaSV){
        listSpinnerMaSV.clear();
        listSpinnerMaSV.add("Chọn mã sinh viên");
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "select MaSV from SinhVien sv where not exists (select MaTk from TaiKhoan tk where sv.MaSV = tk.MaTk)";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maSV = rs.getString("MaSV");
                    listSpinnerMaSV.add(maSV);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner,listSpinnerMaSV);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
    }

    public void insertData(TaiKhoan tk) {
        conn = DatabaseManager.getConnection();
        try {
            if(conn != null) {
                String query = "insert into TaiKhoan(MaTk, TenTaiKhoan, MatKhau, MaVaiTro) " +
                        "VALUES ('"+ tk.getMatk() +"','"+tk.getTentk()+"','"+tk.getMatkhau()+"','VT3')";
                Statement st = conn.createStatement();
                st.executeUpdate(query);
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());

        }
    }
    boolean checkEmpty(EditText edt) {
        CharSequence check = edt.getText().toString();
        return TextUtils.isEmpty(check);
    }

    public void alertSuccess() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(TaoTaiKhoanSV.this);
        bulider.setMessage("Thêm tài khoản thành công.");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(TaoTaiKhoanSV.this, QLTaiKhoanSV.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
}
