package com.example.QLDSV;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Database.DatabaseManager;
import com.example.Objects.LopTinChi;
import com.example.Objects.MonHoc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThemLopTinChi extends AppCompatActivity {
    Connection conn;
    Button btnDateStart, btnXacnhan;
    EditText txtMaMH, txtSltoithieu, txtSltoida, txtNamhoc, txtHocki, txtMaLTC;
    TextView txtDateStart, txtDateEnd;
    Button btnDateEnd;
    Button btnClickback;
    ArrayList<LopTinChi> listLTC = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlloptinchi_add);
        txtDateStart = findViewById(R.id.txtDateStart);
        txtDateEnd = findViewById(R.id.txtDateEnd);

        txtMaLTC = findViewById(R.id.txtMaLTC);
        txtMaMH = findViewById(R.id.txtMaMH);
        txtSltoithieu = findViewById(R.id.txtSltoithieu);
        txtSltoida = findViewById(R.id.txtSltoida);
        txtNamhoc = findViewById(R.id.txtNamhoc);
        txtHocki = findViewById(R.id.txtHocki);
        txtDateStart = findViewById(R.id.txtDateStart);
        txtDateEnd = findViewById(R.id.txtDateEnd);

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
                Intent intent = new Intent(ThemLopTinChi.this, QLLopTinChi.class);
                startActivity(intent);
                finish();
            }
        });

        btnXacnhan = findViewById(R.id.btnXacnhan);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty(txtMaLTC)) {
                    txtMaLTC.setError("Vui lòng nhập mã lớp tín chỉ");
                }
                else if(checkEmpty(txtMaMH)) {
                    txtMaMH.setError("Vui lòng nhập mã môn học.");
                }
                else if(checkEmpty(txtSltoithieu)) {
                    txtSltoithieu.setError("Vui lòng nhập số lượng tối thiểu.");
                }
                else if(checkEmpty(txtSltoida)) {
                    txtSltoida.setError("Vui lòng nhập số lượng tối đa.");
                }
                else if(txtDateStart.getText().equals("")) {
                    btnDateStart.setError("Vui lòng chọn ngày bắt đầu.");
                }
                else if(txtDateEnd.getText().equals("")) {
                    btnDateEnd.setError("Vui lòng chọn ngày kết thúc.");
                }
                else if(checkEmpty(txtNamhoc)) {
                    txtNamhoc.setError("Vui lòng nhập mã môn học.");
                }
                else if(checkEmpty(txtHocki)) {
                    txtHocki.setError("Vui lòng nhập học kì.");
                }
                else {
                    if(checkMaLTC(txtMaLTC.getText().toString().trim()) == true) {
                        alertFail();
                        txtMaLTC.setText(null);
                    }
                    else {
                        java.sql.Date dateStart = (java.sql.Date) StringtoDate(txtDateStart.getText().toString());
                        java.sql.Date dateEnd = (java.sql.Date) StringtoDate(txtDateEnd.getText().toString());
                        LopTinChi ltc = new LopTinChi(txtMaLTC.getText().toString().trim(), txtNamhoc.getText().toString().trim(),
                                txtHocki.getText().toString().trim(),
                                Integer.parseInt(txtSltoithieu.getText().toString()),
                                Integer.parseInt(txtSltoida.getText().toString()), dateStart, dateEnd,
                                txtMaMH.getText().toString().trim());
                        insertData(ltc);
                        alertSuccess();
                    }
                }
            }
        });
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

    boolean checkEmpty(EditText edt) {
        CharSequence check = edt.getText().toString();
        return TextUtils.isEmpty(check);
    }
    public Boolean checkMaLTC(String str) {
        for(Object obj: listLTC) {
            if(obj instanceof LopTinChi) {
                if(str.equals(((LopTinChi) obj).getMaltc())) {
                    return true;
                }
            }
        }
        return false;
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
    public void insertData(LopTinChi ltc) {
        conn = DatabaseManager.getConnection();
        try {
            if(conn != null) {
                String query = "INSERT INTO LopTinChi(MaLTC, NamHoc, HocKi, SLToiThieu, SLToiDa, NgayBD, NgayKT, MaMH) " +
                        "VALUES ('" + ltc.getMaltc().trim() + "', '" + ltc.getNamhoc().trim() + "', '" + ltc.getHocki().trim() + "', " +
                        ltc.getSltoithieu() + ", " + ltc.getSltoida() + ", '" + ltc.getBatdau().toString() + "', '" + ltc.getKetthuc().toString() + "', '" + ltc.getMamh() + "')";
                Statement st = conn.createStatement();
                st.executeUpdate(query);
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());

        }
    }
    public void alertSuccess() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(ThemLopTinChi.this);
        bulider.setMessage("Thêm lớp tín chỉ thành công.");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(ThemLopTinChi.this, QLLopTinChi.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
    public void alertFail() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(ThemLopTinChi.this);
        bulider.setMessage("Thêm lớp tín chỉ thất bại.");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
    public Date StringtoDate(String str) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date) dateFormat.parse(str);
            Date utilDate = dateFormat.parse(dateFormat.format(date));
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            return sqlDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}