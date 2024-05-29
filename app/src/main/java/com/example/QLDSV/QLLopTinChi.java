package com.example.QLDSV;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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

import com.example.Database.DatabaseManager;
import com.example.Objects.LopTinChi;
import com.example.adapter.LopTinChiAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class QLLopTinChi extends AppCompatActivity {
    Connection conn;
    Button btnAddLTC;
    Button btnDelLTC;
    ListView listViewLTC;
    Button btnClickback;
    Spinner spinnerNienKhoa;
    ArrayList<LopTinChi> listLTC = new ArrayList<>();
    SearchView searchView;
    ArrayList<String> listSpinnerNamhoc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlloptinchi_main);
        listViewLTC = findViewById(R.id.listViewLTC);
        spinnerNienKhoa = findViewById(R.id.spinnerNienKhoa);
        searchView = findViewById(R.id.searchLTC);
        loadSpinnerNienKhoa(spinnerNienKhoa);
        searchingLTC();

        btnDelLTC = findViewById(R.id.btnDelLTC);
        btnDelLTC.setEnabled(false);

        listViewLTC.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            int chosingIndex = -1;
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(chosingIndex == -1) {
                    adapterView.getChildAt(i).setBackground(getDrawable(R.color.background2nd));
                    btnDelLTC.setBackground(getDrawable(R.drawable.buttonmain));
                    btnDelLTC.setEnabled(true);
                    btnDelLTC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Iiiii", listLTC.get(i).toString());
                            deleteLTC(i);
                            btnDelLTC.setBackground(getDrawable(R.drawable.mybutton));
                            adapterView.getChildAt(i).setBackground(null);
                            btnDelLTC.setEnabled(false);
                        }
                    });
                    chosingIndex = i;
                }
                else {
                    adapterView.getChildAt(i).setBackground(getDrawable(R.color.background2nd));
                    adapterView.getChildAt(chosingIndex).setBackground(null);
                    chosingIndex = -1;
                    btnDelLTC.setBackground(getDrawable(R.drawable.buttonmain));
                    btnDelLTC.setEnabled(true);
                    btnDelLTC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Iiiii", listLTC.get(i).toString());
                            deleteLTC(i);
                            btnDelLTC.setBackground(getDrawable(R.drawable.mybutton));
                            adapterView.getChildAt(i).setBackground(null);
                            btnDelLTC.setEnabled(false);
                        }
                    });
                }

                return true;
            }
        });
        spinnerNienKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String namhoc = listSpinnerNamhoc.get(i).toString();
                Log.i("Tagg", namhoc);
                if(namhoc.equals("Chọn tất cả năm học")) {
                    loadDataListView();
                    listViewLTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(QLLopTinChi.this, QLLopTinChi_CT.class);
                            intent.putExtra("maltc", listLTC.get(i).getMaltc());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    loadDataListViewNamHoc(namhoc);
                    listViewLTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(QLLopTinChi.this, QLLopTinChi_CT.class);
                            intent.putExtra("maltc", listLTC.get(i).getMaltc());
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddLTC = findViewById(R.id.btnAddLTC);
        btnAddLTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLLopTinChi.this, ThemLopTinChi.class);
                startActivity(intent);
                finish();
            }
        });
        btnDelLTC = findViewById(R.id.btnDelLTC);
        btnDelLTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLLopTinChi.this, ThemLopTinChi.class);
                startActivity(intent);
                finish();
            }
        });
        btnClickback = findViewById(R.id.btnClickback);
        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QLLopTinChi.this, TrangChuPGV.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void searchingLTC() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<LopTinChi> listLTCDB = new ArrayList<>();
                try {
                    conn = DatabaseManager.getConnection();
                    if(conn != null) {
                        String query = "SELECT MaLTC, NamHoc FROM LopTinChi";
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        while (rs.next()) {
                            String maLTC = rs.getString("MaLTC");
                            String namhoc = rs.getString("NamHoc");
                            LopTinChi ltc = new LopTinChi(maLTC, namhoc);
                            Log.e("MAltc", ltc.getMaltc());
                            Log.e("namhoc", ltc.getNamhoc());
                            listLTCDB.add(ltc);
                        }
                    }
                }
                catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                ArrayList<LopTinChi> listSearching = new ArrayList<>();
                for (LopTinChi ltc : listLTCDB) {
                    if (ltc.getMaltc().toLowerCase().contains(newText.toLowerCase()) ||
                            ltc.getNamhoc().toLowerCase().contains(newText.toLowerCase())) {
                        Log.e("namhoc", ltc.getNamhoc());
                        listSearching.add(ltc);
                    }
                }
                LopTinChiAdapter adapter = new LopTinChiAdapter(getApplicationContext(), R.layout.list_loptinchi, listSearching);
                listViewLTC.setAdapter(adapter);
                return false;
            }
        });
    }
    public void loadDataListView() {
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM LopTinChi";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maltc = rs.getString("MaLTC");
                    String nh = rs.getString("NamHoc");
                    String hk = rs.getString("HocKi");
                    LopTinChi ltc = new LopTinChi(maltc, nh, hk);
                    listLTC.add(ltc);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        LopTinChiAdapter adapter = new LopTinChiAdapter(this,R.layout.list_loptinchi,listLTC);
        listViewLTC.setAdapter(adapter);
    }
    public void loadDataListViewNamHoc(String str) {
        listLTC = new ArrayList<>();
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT * FROM LopTinChi WHERE NamHoc = '" + str + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maltc = rs.getString("MaLTC");
                    String nh = rs.getString("NamHoc");
                    String hk = rs.getString("HocKi");
                    LopTinChi ltc = new LopTinChi(maltc, nh, hk);
                    listLTC.add(ltc);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        LopTinChiAdapter adapter = new LopTinChiAdapter(QLLopTinChi.this,R.layout.list_loptinchi,listLTC);
        listViewLTC.setAdapter(adapter);
    }
    public void loadSpinnerNienKhoa(Spinner spinner) {
        listSpinnerNamhoc.add("Chọn tất cả năm học");
        listSpinnerNamhoc.add("2019-2020");
        listSpinnerNamhoc.add("2020-2021");
        listSpinnerNamhoc.add("2021-2022");
        listSpinnerNamhoc.add("2022-2023");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item,listSpinnerNamhoc);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_chuyennganh);
        spinner.setAdapter(adapter);
    }
    public ArrayList<String> loadDataDangKi(String str) {
        ArrayList<String> listDangKi = new ArrayList<>();
        try {
            conn = DatabaseManager.getConnection();
            if(conn != null) {
                String query = "SELECT TOP 1 MaLTC FROM DangKi WHERE MaLTC = '" + str + "'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String maLTC = rs.getString("MaLTC");
                    listDangKi.add(maLTC);
                }
            }
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return listDangKi;
    }
    public void deleteLTC(int i) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(QLLopTinChi.this);
        bulider.setMessage("Xác nhận xoá lớp tín chỉ này?");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String maltc = listLTC.get(i).getMaltc();
                LopTinChi ltc = listLTC.get(i);
                ArrayList<String> listDangKi = loadDataDangKi(maltc);
                if(listDangKi.size() == 0) {
                    listLTC.remove(ltc);
                    try {
                        String query = "DELETE FROM LopTinChi WHERE MaLTC = '" + maltc + "'";
                        PreparedStatement pst = conn.prepareStatement(query);
                        pst.executeUpdate();
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                    loadDataListView();
                }
                else {
                    deleteFail(listLTC.get(i).getMaltc());
                }
                listDangKi = new ArrayList<>();
                dialog.cancel();
            }
        });
        bulider.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }
    public void deleteFail(String str) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(QLLopTinChi.this);
        bulider.setMessage("Không thể xoá lớp tín chỉ " + str.trim() + ". Do lớp tín chỉ đã có người đăng kí");
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
        bulider.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
    }
}