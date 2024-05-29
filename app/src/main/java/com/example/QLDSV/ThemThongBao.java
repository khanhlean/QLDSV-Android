package com.example.QLDSV;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemThongBao extends AppCompatActivity {

    Spinner cbOption;
    Button btnThem, btnClickback;
    EditText etTieuDe,etNoiDung;
    LinearLayout loChonLop ,loChonSV;
    AutoCompleteTextView cbLop,cbSinhVien;
    ArrayList<String> arrOption = new ArrayList<String>();
    String maGV="", vaiTro="";
    int opt =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thong_bao);
        Intent intent = getIntent();
        maGV = intent.getStringExtra("maGiangVien");
        vaiTro = intent.getStringExtra("vaiTro");

        setControl();
        addItem();
        setEvent();
    }

    private void setEvent() {
        cbOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Lớp")) {
                    loChonLop.setVisibility(View.VISIBLE);
                    loChonSV.setVisibility(View.GONE);
                    opt=2;
                }
                else if (selectedItem.equals("Sinh Viên")) {
                    loChonSV.setVisibility(View.VISIBLE);
                    loChonLop.setVisibility(View.GONE);
                    opt=3;
                }
                else {
                    loChonLop.setVisibility(View.GONE);
                    loChonSV.setVisibility(View.GONE);
                    opt=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                if(opt == 1) {

                    themThongBaoChoTatCaSinhVien(etTieuDe.getText().toString(), etNoiDung.getText().toString(), currentDate.toString(), maGV);

                }else if(opt == 2 ){
                    themThongBaoTheoLop(etTieuDe.getText().toString(), etNoiDung.getText().toString(), currentDate.toString(), maGV, cbLop.getText().toString());
                }else if(opt == 3 ){
                    themThongBaoChoMotSinhVien(etTieuDe.getText().toString(), etNoiDung.getText().toString(),currentDate.toString(), maGV,cbSinhVien.getText().toString());
                }
                AlertDialog.Builder bulider = new AlertDialog.Builder(ThemThongBao.this);
                bulider.setMessage("Thêm thông báo thành công");
                bulider.setCancelable(true);
                bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = bulider.create();
                alert.show();
            }
        });

        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vaiTro.equals("PGV")){
                    Intent intent = new Intent(ThemThongBao.this, TrangChuPGV.class);
                    intent.putExtra("maGiangVien", maGV);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ThemThongBao.this, TrangChuGV.class);
                    intent.putExtra("maGiangVien", maGV);
                    startActivity(intent);
                }
            }

        });
    }

    private void setControl() {
        cbOption=  findViewById(R.id.cbOption);
        loChonLop=  findViewById(R.id.lo_chonlop);
        loChonSV=  findViewById(R.id.lo_chonsv);
        cbLop=  findViewById(R.id.cbLop);
        cbSinhVien=  findViewById(R.id.cbSinhVien);
        btnThem=  findViewById(R.id.btn_themthongbao);
        etTieuDe=  findViewById(R.id.et_tieude);
        etNoiDung=  findViewById(R.id.et_noidung);
        btnClickback = findViewById(R.id.btnClickback);
    }

    private void addItem() {
        if(vaiTro.equals("PGV")) {
            arrOption.add("Toàn Bộ");
            arrOption.add("Lớp");
            arrOption.add("Sinh Viên");
        }else{
            arrOption.add("Lớp");
            arrOption.add("Sinh Viên");
        }
        ArrayAdapter arrayAdapterOption = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrOption);
        cbOption.setAdapter(arrayAdapterOption);
        ArrayAdapter<String> adaptermasv = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getAllMaSV()); // Tạo ArrayAdapter
        cbSinhVien.setAdapter(adaptermasv);
        cbSinhVien.setThreshold(0);
        ArrayAdapter<String> adaptermalop = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getAllMaLop()); // Tạo ArrayAdapter
        cbLop.setAdapter(adaptermalop);
        cbLop.setThreshold(0);
    }

    public void themThongBaoChoTatCaSinhVien(String tieuDe, String noiDung, String ngayGui, String maGV) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO ThongBao (TieuDe, NoiDung, NgayGui, MaGV) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tieuDe);
            statement.setString(2, noiDung);
            statement.setString(3, ngayGui);
            statement.setString(4, maGV);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting thong bao failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int maTB = generatedKeys.getInt(1);
                String sqlSinhVienThongBao = "INSERT INTO SinhVien_ThongBao (MaSV, MaTB) SELECT MaSV, ? FROM SinhVien";
                PreparedStatement statementSinhVienThongBao = conn.prepareStatement(sqlSinhVienThongBao);
                statementSinhVienThongBao.setInt(1, maTB);
                statementSinhVienThongBao.executeUpdate();
                statementSinhVienThongBao.close();
                alertSuccess("Thêm thông báo thành công!");
            } else {
                throw new SQLException("Inserting thong bao failed, no ID obtained.");
            }

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void themThongBaoTheoLop(String tieuDe, String noiDung, String ngayGui, String maGV, String maLop) {
            try {
                Connection conn = DatabaseManager.getConnection();

                // Thêm thông báo mới vào bảng ThongBao
                String sql = "INSERT INTO ThongBao (TieuDe, NoiDung, NgayGui, MaGV) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, tieuDe);
                statement.setString(2, noiDung);
                statement.setString(3, ngayGui);
                statement.setString(4, maGV);
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Inserting thong bao failed, no rows affected.");
                }

                ResultSet generatedKeys = statement.getGeneratedKeys();
                int maTB;
                if (generatedKeys.next()) {
                    maTB = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting thong bao failed, no ID obtained.");
                }

                statement.close();

                // Thêm thông báo mới cho tất cả sinh viên trong lớp
                String sqlSinhVienThongBao = "INSERT INTO SinhVien_ThongBao (MaSV, MaTB) SELECT MaSV, ? FROM SinhVien WHERE MaLop = (SELECT MaLop FROM LopTinChi WHERE MaLTC = ?)";
                PreparedStatement statementSinhVienThongBao = conn.prepareStatement(sqlSinhVienThongBao);
                statementSinhVienThongBao.setInt(1, maTB);
                statementSinhVienThongBao.setString(2, maLop);
                statementSinhVienThongBao.executeUpdate();
                statementSinhVienThongBao.close();
                alertSuccess("Thêm thông báo thành công!");

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
    public void themThongBaoChoMotSinhVien( String tieuDe, String noiDung, String ngayGui, String maGV,String maSV) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO ThongBao (TieuDe, NoiDung, NgayGui, MaGV) VALUES (?, ?, ?, ?)";
            System.out.println(sql);
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tieuDe);
            statement.setString(2, noiDung);
            statement.setString(3, ngayGui);
            statement.setString(4, maGV);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting thong bao failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            int maTB;
            if (generatedKeys.next()) {
                maTB = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting thong bao failed, no ID obtained.");
            }

            String sqlSinhVienThongBao = "INSERT INTO SinhVien_ThongBao (MaSV, MaTB) VALUES (?, ?)";
            PreparedStatement statementSinhVienThongBao = conn.prepareStatement(sqlSinhVienThongBao);
            statementSinhVienThongBao.setString(1, maSV);
            statementSinhVienThongBao.setInt(2, maTB);
            statementSinhVienThongBao.executeUpdate();
            statementSinhVienThongBao.close();

            statement.close();
            conn.close();

            alertSuccess("Thêm thông báo thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alertSuccess(String content) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(ThemThongBao.this);
        bulider.setMessage(content);
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(ThemThongBao.this, TrangChuGV.class);
                intent.putExtra("maGiangVien", TrangChuGV.maGV);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }

    private List<String> getAllMaSV() {
        List<String> listMaSV = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT MaSV FROM SinhVien";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maSV = rs.getString("MaSV");
                listMaSV.add(maSV);
            }

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMaSV;
    }
    private List<String> getAllMaLop() {
        List<String> listMaLTC = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT MaLTC FROM LopTinChi";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String MaLTC = rs.getString("MaLTC");
                listMaLTC.add(MaLTC);
            }

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMaLTC;
    }
}