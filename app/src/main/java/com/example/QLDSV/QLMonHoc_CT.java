package com.example.QLDSV;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Database.DatabaseManager;
import com.example.Objects.MonHoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class QLMonHoc_CT extends AppCompatActivity {
    Connection conn;
    String mamh;
    Button btnAccept, btnBack;
    TextView txtMaMH;
    EditText txtTenMH, txtLT, txtTH, txtTinchi, txtCC, txtGK, txtCK, txtMaCN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlmonhoc_details);
        Intent intent = getIntent();
        mamh = intent.getStringExtra("mamh");
        Log.i("MAMH", mamh);
        MonHoc monhoc = loadInfoMonhoc(mamh);

        txtMaMH = findViewById(R.id.txtMaMH);
        txtTenMH = findViewById(R.id.txtTenMH);
        txtLT = findViewById(R.id.txtLT);
        txtTH = findViewById(R.id.txtTH);
        txtTinchi = findViewById(R.id.txtTinchi);
        txtCC = findViewById(R.id.txtCC);
        txtGK = findViewById(R.id.txtGK);
        txtCK = findViewById(R.id.txtCK);
        txtMaCN = findViewById(R.id.txtMaCN);

        txtMaMH.setText(monhoc.getMamh());
        txtTenMH.setText(monhoc.getTenmh());
        txtLT.setText(monhoc.getTietLT()+"");
        txtTH.setText(monhoc.getTietTH()+"");
        txtTinchi.setText(monhoc.getTinchi()+"");
        txtCC.setText(monhoc.getCc()+"");
        txtGK.setText(monhoc.getGk()+"");
        txtCK.setText(monhoc.getCk()+"");
        txtMaCN.setText(monhoc.getMacn());

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLMonHoc_CT.this, QLMonHoc.class);
                startActivity(intent);
                finish();
            }
        });
        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonHoc monhoc = new MonHoc(txtMaMH.getText().toString(), txtTenMH.getText().toString(),
                        Integer.parseInt(txtLT.getText().toString()), Integer.parseInt(txtTH.getText().toString()),
                        Integer.parseInt(txtTinchi.getText().toString()), Integer.parseInt(txtCC.getText().toString()),
                        Integer.parseInt(txtGK.getText().toString()), Integer.parseInt(txtCK.getText().toString()),
                        txtMaCN.getText().toString());
                try {
                    conn = DatabaseManager.getConnection();
                    String query = "UPDATE MonHoc SET TenMH = ?, SoTietLT = ?, SoTietTH = ?, SoTinChi = ?, HeSoCC = ?, " +
                            "HeSoGK = ?, HeSoCK = ?, MaCN = ? WHERE MaMH = '" + monhoc.getMamh() + "'";
                    Log.e("Success", "2");

                    PreparedStatement pst = conn.prepareStatement(query);
                    Log.e("Success", "3");
                    pst.setString(1, monhoc.getTenmh());
                    pst.setInt(2, monhoc.getTietLT());
                    pst.setInt(3, monhoc.getTietTH());
                    pst.setInt(4, monhoc.getTinchi());
                    pst.setInt(5, monhoc.getCc());
                    pst.setInt(6, monhoc.getGk());
                    pst.setInt(7, monhoc.getCk());
                    pst.setString(8, monhoc.getMacn());
                    pst.executeUpdate();
                    Log.e("Success", "4");
                }
                catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                finish();
            }
        });
    }
    public MonHoc loadInfoMonhoc(String mamh) {
        MonHoc mh = null;
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM MonHoc WHERE MonHoc.MaMH = '" + mamh + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String ma = rs.getString("MaMH");
                    String ten = rs.getString("TenMH");
                    int lt = Integer.parseInt(rs.getString("SoTietLT"));
                    int th = Integer.parseInt(rs.getString("SoTietTH"));
                    int tc = Integer.parseInt(rs.getString("SoTinChi"));
                    int cc = Integer.parseInt(rs.getString("HeSoCC"));
                    int gk = Integer.parseInt(rs.getString("HeSoGK"));
                    int ck = Integer.parseInt(rs.getString("HeSoCK"));
                    String cn = rs.getString("MaCN");
                    mh = new MonHoc(ma, ten, lt, th, tc, cc, gk, ck, cn);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return mh;
    }

}