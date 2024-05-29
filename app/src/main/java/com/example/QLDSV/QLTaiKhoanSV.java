package com.example.QLDSV;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.Database.DatabaseManager;
import  com.example.adapter.*;
import com.example.Objects.TaiKhoan;
import  com.example.Objects.Lop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QLTaiKhoanSV extends AppCompatActivity {
    Button btnClickback;
    Button btnThemTaiKhoan , btnXoaTaiKhoan;
    Connection conn;
    ListView listTK;
    Spinner spinnerLop;

    SearchView searchTK;
    ArrayList<TaiKhoan> list = new ArrayList<>();
    ArrayList<String> listSnpinnerLop = new ArrayList<>();

    ArrayList<Lop> listLop = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltaikhoansv);

        listTK = findViewById(R.id.listViewTK);

        spinnerLop = (Spinner) findViewById(R.id.spinnerLop);
        searchTK = findViewById(R.id.searchTaiKhoan);

        loadSpinnerLop(spinnerLop, listSnpinnerLop);
        loadLop();
        searchTaiKhoan();

        spinnerLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tenLop = listSnpinnerLop.get(i).toString();
                getMaLop(tenLop);
                if(getMaLop(tenLop).equals("")){
                    loadListTaiKhoan();
                    listTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(QLTaiKhoanSV.this, TaiKhoanSV_CT.class);
                            Log.i("MaMh", list.get(i).getMatk());
                            intent.putExtra("maTK", list.get(i).getMatk());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    loadListTKTheoLop(getMaLop(tenLop));
                    listTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(QLTaiKhoanSV.this, TaiKhoanSV_CT.class);
                            intent.putExtra("maTK", list.get(i).getMatk());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnClickback = findViewById(R.id.btnClickback);
        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLTaiKhoanSV.this, TrangChuPGV.class);
                startActivity(intent);
            }

        });

        btnThemTaiKhoan = findViewById(R.id.btnThemTK);
        btnThemTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLTaiKhoanSV.this, TaoTaiKhoanSV.class);
                startActivity(intent);
                finish();
            }
        });

        btnXoaTaiKhoan = findViewById(R.id.btnXoaTK);
        btnXoaTaiKhoan.setEnabled(false);
        listTK.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getChildAt(i).setBackground(getDrawable(R.color.background2nd));
                btnXoaTaiKhoan.setBackground(getDrawable(R.drawable.buttonmain));
                btnXoaTaiKhoan.setEnabled(true);
                btnXoaTaiKhoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("Iiiii", list.get(i).toString());
                        XoaTaiKhoan(i);
                        btnXoaTaiKhoan.setBackground(getDrawable(R.drawable.mybutton));
                        adapterView.getChildAt(i).setBackground(null);
                    }
                });
                return true;
            }
        });
    };

    public void loadSpinnerLop(Spinner spinner, ArrayList listSnpinnerLop) {
        listSnpinnerLop.clear();
        listSnpinnerLop.add("Chọn lớp");
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "select * from Lop";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String tenLop = rs.getString("TenLop");
                    listSnpinnerLop.add(tenLop);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner,listSnpinnerLop);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
    }

    public void loadListTaiKhoan() {
        list.clear();
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "select MaTk, HoTen from TaiKhoan tk, SinhVien sv where tk.MaTk = sv. MaSV and tk.MaVaitro = 'VT3' ";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String matk = rs.getString("MaTk");
                    String tentk = rs.getString("HoTen");
                    TaiKhoan tk = new TaiKhoan(matk, tentk);
                    list.add(tk);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        TaiKhoanAdapter adapter = new TaiKhoanAdapter(this,R.layout.list_taikhoan,list);
        listTK.setAdapter(adapter);
    }

    public String getMaLop(String str) {
        String MaLop = "";
        for(Object obj: listLop) {
            if(obj instanceof Lop) {
                if(str.equals(((Lop) obj).getTenLop())) {
                    MaLop = ((Lop) obj).getMaLop();
                }
            }
        }
        return MaLop;
    }

    public void loadLop() {
        listLop.clear();
        try {
            conn = DatabaseManager.getConnection();
            if (conn != null) {
                String query = "SELECT * FROM Lop";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maLop = rs.getString("MaLop");
                    String tenLop = rs.getString("TenLop");
                    Lop lop = new Lop(maLop, tenLop);
                    listLop.add(lop);
                }
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    public void loadListTKTheoLop(String str) {
        list.clear();
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT MaTk, HoTen FROM TaiKhoan tk, SinhVien sv where tk.MaTk = sv. MaSV and tk.MaVaitro = 'VT3' and sv.MaLop = '"+str+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maTK = rs.getString("MaTk");
                    String tenTK = rs.getString("HoTen");
                    TaiKhoan tk = new TaiKhoan(maTK, tenTK);
                    list.add(tk);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        TaiKhoanAdapter adapter = new TaiKhoanAdapter(this,R.layout.list_taikhoan,list);
        listTK.setAdapter(adapter);
    }

    public void searchTaiKhoan() {
        searchTK.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<TaiKhoan> listTKtemp = new ArrayList<>();
                try {
                    conn = DatabaseManager.getConnection();
                    if(conn != null) {
                        String query = "select MaTk, HoTen from TaiKhoan tk, SinhVien sv where tk.MaTk = sv. MaSV and tk.MaVaitro = 'VT3'";
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        while (rs.next()) {
                            String maTK = rs.getString("MaTk");
                            String tenTK = rs.getString("HoTen");
                            TaiKhoan tk = new TaiKhoan(maTK, tenTK);
                            listTKtemp.add(tk);
                        }
                    }
                }
                catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                ArrayList<TaiKhoan> listSearchTK = new ArrayList<>();
                for (TaiKhoan tk : listTKtemp) {
                    if (tk.getTentk().toLowerCase().contains(newText.toLowerCase())) {
                        listSearchTK.add(tk);
                    }
                }
                TaiKhoanAdapter adapter = new TaiKhoanAdapter(getApplicationContext(), R.layout.list_taikhoan, listSearchTK);
                listTK.setAdapter(adapter);

                return false;
            }
        });
    }


    public void XoaTaiKhoan(int i) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(QLTaiKhoanSV.this);
        bulider.setMessage("Xác nhận xoá tài khoản này?");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String matk = list.get(i).getMatk();
                TaiKhoan tk = list.get(i);
                list.remove(tk);
                try {
                    String query = "delete from Taikhoan where MaTk = '" + matk + "'";
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.executeUpdate();
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                String lop = spinnerLop.getSelectedItem().toString();
                if(getMaLop(lop) == ""){
                    loadListTaiKhoan();
                }
                else{
                    loadListTKTheoLop(getMaLop(lop));
                }
            }
        });

        bulider.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
        btnXoaTaiKhoan.setEnabled(false);
    }


}
