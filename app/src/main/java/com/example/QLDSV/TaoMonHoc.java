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

import com.example.Database.DatabaseManager;
import com.example.Objects.ChuyenNganh;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Objects.MonHoc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TaoMonHoc extends AppCompatActivity {
    Connection conn;
    Spinner spinnerCN;
    Button btnAddmonhoc, btnClickBack;
    EditText txtMaMH, txtTenMH, txtLT, txtTH, txtTinchi, txtCC, txtGK, txtCK;
    ArrayList<String> listTenCN = new ArrayList<>();
    ArrayList<ChuyenNganh> listCN = new ArrayList<>();
    ArrayList<MonHoc> listMH = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlmonhoc_taomonhoc);

        spinnerCN = findViewById(R.id.spinnerCN);
        btnAddmonhoc = findViewById(R.id.btnAddmonhoc);
        btnClickBack = findViewById(R.id.btnClickback);
        txtMaMH = findViewById(R.id.txtMaMH);
        txtTenMH = findViewById(R.id.txtTenMH);
        txtLT = findViewById(R.id.txtLT);
        txtTH = findViewById(R.id.txtTH);
        txtTinchi = findViewById(R.id.txtTinchi);
        txtCC = findViewById(R.id.txtCC);
        txtGK = findViewById(R.id.txtGK);
        txtCK = findViewById(R.id.txtCK);

        loadData(listCN);
        loadMonHoc(listMH);
        loadSpinner(spinnerCN, listTenCN);
        btnAddmonhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty(txtMaMH)) {
                    txtMaMH.setError("Vui lòng nhập mã môn học.");
                }
                else if(checkEmpty(txtTenMH)) {
                    txtTenMH.setError("Vui lòng nhập tên môn học.");
                }
                else if(checkEmpty(txtLT)) {
                    txtLT.setError("Vui lòng nhập số tiết lí thuyết.");
                }
                else if(checkEmpty(txtTH)) {
                    txtTH.setError("Vui lòng nhập số tiết thực hành.");
                }
                else if(checkEmpty(txtTinchi)) {
                    txtTinchi.setError("Vui lòng nhập số tín chỉ.");
                }
                else {
                    if(checkMaMH(txtMaMH.getText().toString().trim()) == true) {
                        alertFail();
                        txtMaMH.setText(null);
                    }
                    else if(checkTenMH(txtTenMH.getText().toString().trim()) == true) {
                        alertFail();
                        txtTenMH.setText(null);
                    }
                    else {
                        String itemSelected = spinnerCN.getSelectedItem().toString();
//                        Log.i("Selected", itemSelected);
//                        String temp = getMaCN(itemSelected);
//                        Log.i("Taggg", temp);
//                        Log.i("MaMH", txtMaMH.getText().toString());
                        MonHoc mh = new MonHoc(txtMaMH.getText().toString().trim(), txtTenMH.getText().toString().trim(),
                                Integer.parseInt(txtLT.getText().toString()), Integer.parseInt(txtTH.getText().toString()),
                                Integer.parseInt(txtTinchi.getText().toString()), Integer.parseInt(txtCC.getText().toString()),
                                Integer.parseInt(txtGK.getText().toString()), Integer.parseInt(txtCK.getText().toString()),
                                getMaCN(itemSelected));
                        insertData(mh);
                        alertSuccess();
                    }
                }
            }
        });

        btnClickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaoMonHoc.this, QLMonHoc.class);
                startActivity(intent);
                finish();
            }
        });
//        String itemSelected = spinnerCN.getSelectedItem().toString();
//        Toast.makeText(QlmonhocTaomonhocActivity.this, "Clicked on : " + itemSelected, Toast.LENGTH_LONG).show();
    }
    public String getMaCN(String str) {
        String MaCN = "";
        for(Object obj: listCN) {
            if(obj instanceof ChuyenNganh) {
                if(str.equals(((ChuyenNganh) obj).getTencn())) {
                    MaCN = ((ChuyenNganh) obj).getMacn();
                }
            }
        }
        return MaCN;
    }
    public Boolean checkMaMH(String str) {
        for(Object obj: listMH) {
            if(obj instanceof MonHoc) {
                if(str.equals(((MonHoc) obj).getMamh())) {
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean checkTenMH(String str) {
        for(Object obj: listMH) {
            if(obj instanceof MonHoc) {
                if(str.equals(((MonHoc) obj).getTenmh())) {
                    return true;
                }
            }
        }
        return false;
    }
    public void loadMonHoc(ArrayList list) {
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM MonHoc";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String mamh = rs.getString("MaMH");
                    String tenmh = rs.getString("TenMH");
                    MonHoc mh = new MonHoc(mamh, tenmh);
                    list.add(mh);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    public void loadSpinner(Spinner spinner, ArrayList listTenCN) {
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT TenCN FROM ChuyenNganh";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String tencn = rs.getString("TenCN");
                    listTenCN.add(tencn);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item,listTenCN);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_chuyennganh);
        spinner.setAdapter(adapter);
    }
    public void loadData(ArrayList listCN) {
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM ChuyenNganh";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String macn = rs.getString("MaCN");
                    String tencn = rs.getString("TenCN");
                    ChuyenNganh cn = new ChuyenNganh(macn, tencn);
                    listCN.add(cn);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    public void insertData(MonHoc mh) {
        conn = DatabaseManager.getConnection();
        try {
            if(conn != null) {
                String query = "INSERT INTO MonHoc(MaMH, TenMH, SoTietLT, SoTietTH, SoTinChi, HeSoCC, HeSoGK, HeSoCK, MaCN) " +
                        "VALUES ('" + mh.getMamh().trim() + "', '" + mh.getTenmh().trim() + "', " + mh.getTietLT() + ", " +
                        mh.getTietTH() + ", " + mh.getTinchi() + ", " + mh.getCc() + ", " + mh.getGk() + ", " + mh.getCk() + ", '" +
                        mh.getMacn() + "')";
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
        AlertDialog.Builder bulider = new AlertDialog.Builder(TaoMonHoc.this);
        bulider.setMessage("Thêm môn học thành công.");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(TaoMonHoc.this, QLMonHoc.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
    public void alertFail() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(TaoMonHoc.this);
        bulider.setMessage("Thêm môn học thất bại.");
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