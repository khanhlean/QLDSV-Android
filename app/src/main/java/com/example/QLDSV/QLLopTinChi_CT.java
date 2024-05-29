package com.example.QLDSV;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Database.DatabaseManager;
import com.example.Objects.LopTinChi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QLLopTinChi_CT extends AppCompatActivity {
    Connection conn;
    EditText txtMaMH, txtSltoithieu, txtSltoida, txtNamhoc, txtHocki;
    TextView txtDateStart, txtDateEnd, txtMaLTC;
    Button btnXacnhan, btnClickback;
    Button btnDateEnd;
    Button btnDateStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlloptinchi_detail);

        txtMaLTC = findViewById(R.id.txtMaLTC);
        txtMaMH = findViewById(R.id.txtMaMH);
        txtSltoithieu = findViewById(R.id.txtSltoithieu);
        txtSltoida = findViewById(R.id.txtSltoida);
        txtNamhoc = findViewById(R.id.txtNamhoc);
        txtHocki = findViewById(R.id.txtHocki);
        txtDateStart = findViewById(R.id.txtDateStart);
        txtDateEnd = findViewById(R.id.txtDateEnd);

        Intent intent = getIntent();
        String maltc = intent.getStringExtra("maltc");
        LopTinChi ltc = loadInfoLopTinChi(maltc);
        txtMaLTC.setText(ltc.getMaltc());
        txtMaMH.setText(ltc.getMamh());
        txtSltoithieu.setText(String.valueOf(ltc.getSltoithieu()));
        txtSltoida.setText(String.valueOf(ltc.getSltoida()));
        txtNamhoc.setText(ltc.getNamhoc());
        txtHocki.setText(ltc.getHocki());
        txtDateStart.setText(ltc.getBatdau().toString());
        txtDateEnd.setText(ltc.getKetthuc().toString());

        btnDateStart = findViewById(R.id.btnDateStart);
        btnDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker(txtDateStart);
            }
        });
        btnDateEnd = findViewById(R.id.btnDateEnd);
        btnDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker(txtDateEnd);
            }
        });

        btnClickback = findViewById(R.id.btnClickback);
        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXacnhan = findViewById(R.id.btnXacnhan);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.sql.Date dateStart = (java.sql.Date) StringtoDate(txtDateStart.getText().toString());
                java.sql.Date dateEnd = (java.sql.Date) StringtoDate(txtDateEnd.getText().toString());
                LopTinChi ltc = new LopTinChi(txtMaLTC.getText().toString(), txtNamhoc.getText().toString(),
                        txtHocki.getText().toString(), Integer.parseInt(txtSltoithieu.getText().toString()),
                        Integer.parseInt(txtSltoida.getText().toString()), dateStart,
                        dateStart, txtMaMH.getText().toString());
                try {
                    conn = DatabaseManager.getConnection();
                    String query = "UPDATE LopTinChi SET NamHoc = ?, HocKi = ?, SLToiThieu = ?, SLToiDa = ?, NgayBD = ?, " +
                            "NgayKT = ?, MaMH = ? WHERE MaLTC = '" + ltc.getMaltc() + "'";

                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setString(1, ltc.getNamhoc());
                    pst.setString(2, ltc.getHocki());
                    pst.setInt(3, ltc.getSltoithieu());
                    pst.setInt(4, ltc.getSltoida());
                    pst.setDate(5, dateStart);
                    pst.setDate(6, dateEnd);
                    pst.setString(7, ltc.getMamh());
                    pst.executeUpdate();
                }
                catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                finish();
            }
        });

    }

    public LopTinChi loadInfoLopTinChi(String maltc) {
        LopTinChi ltc = null;
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM LopTinChi WHERE LopTinChi.MaLTC = '" + maltc + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maLTC = rs.getString("MaLTC");
                    String namhoc = rs.getString("NamHoc");
                    String hocki = rs.getString("HocKi");
                    int slToiThieu = Integer.parseInt(rs.getString("SLToiThieu"));
                    int slToiDa = Integer.parseInt(rs.getString("SLToiDa"));
                    Date ngayBD = StringtoDate(rs.getString("NgayBD"));
                    Date ngayKT = StringtoDate(rs.getString("NgayKT"));
                    String maMH = rs.getString("MaMH");
                    ltc = new LopTinChi(maLTC, namhoc, hocki, slToiThieu, slToiDa,
                            (java.sql.Date) ngayBD, (java.sql.Date) ngayKT, maMH);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return ltc;
    }
    private void DatePicker(TextView setDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String str = i + "-" + (i1+1) + "-" +i2;
                Log.e("Date", str);
                setDate.setText(str);
            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public Date StringtoDate(String str) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date) dateFormat.parse(str);
//            Log.e("Date", String.valueOf(dateFormat.format(date)));
            Date utilDate = dateFormat.parse(dateFormat.format(date));
//            Log.e("DateUtil", String.valueOf(utilDate));
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//            Log.e("DateSQL", String.valueOf(sqlDate));

            return sqlDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}