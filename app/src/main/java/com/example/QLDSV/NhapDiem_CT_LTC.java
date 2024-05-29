package com.example.QLDSV;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.Database.DatabaseManager;
import com.example.Objects.DiemSinhVien;
import com.example.adapter.DiemSinhVienAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NhapDiem_CT_LTC extends AppCompatActivity {
    SearchView searchSV;
    Connection connect;
    String connectionResult="";
    ListView lvDSSV_LTC;
    public static ArrayList<DiemSinhVien> arrDiemSV;
    DiemSinhVien objectDiemSinhVien;
    DiemSinhVienAdapter diemSinhVienAdapter;
    Context context;
    TextView maLTC, tenMH;

    Button btnClickback;

    String MaGiangVien = "";

    public static String maLTC_ct_ltc="", maSV_ct_ltc="", tenMH_ct_ltc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapdiem_ct_ltc);

        Intent intent = getIntent();
        MaGiangVien = intent.getStringExtra("maGiangVien");

        setControl();
        setEvent();
        searchTaiKhoan();

        diemSinhVienAdapter = new DiemSinhVienAdapter(this,R.layout.item_ctltc_nhapdiem,arrDiemSV);
        lvDSSV_LTC.setAdapter(diemSinhVienAdapter);
        lvDSSV_LTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NhapDiem_CT_LTC.this, "Item: "+ NhapDiem_CT_LTC.arrDiemSV.get(position).getMaSV(), Toast.LENGTH_SHORT).show();
                maLTC_ct_ltc = NhapDiem.maLTC_NhapDiem;
                tenMH_ct_ltc = NhapDiem.tenMH_NhapDiem;
                maSV_ct_ltc = NhapDiem_CT_LTC.arrDiemSV.get(position).getMaSV();
                Intent intent = new Intent(NhapDiem_CT_LTC.this, CapNhatDiem.class);
                startActivity(intent);
            }
        });
        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhapDiem_CT_LTC.this, NhapDiem.class);
                intent.putExtra("maGiangVien", MaGiangVien);
                startActivity(intent);
            }

        });
    }


        private void getDSSV() {
        try {
            connect = DatabaseManager.getConnection();

            if(connect !=null){
                String query =
                        "SELECT SV.MaSV, DK.DIEMCC,DK.DiemGK,DK.DiemCK,(MH.HeSoCC*DK.DiemCC + MH.HeSoGK*DK.DiemGK + MH.HeSocK*DK.DiemGK)/100 AS DIEMTK  \n" +
                        "FROM LOPTINCHI LTC, SINHVIEN SV,MONHOC MH, DangKi DK\n" +
                        "WHERE DK.MaLTC='"+ NhapDiem.maLTC_NhapDiem+"' AND DK.MaSV=SV.MaSV AND LTC.MaMH=MH.MaMH and DK.MALTC = LTC.MALTC";
                arrDiemSV.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {

                    objectDiemSinhVien = new DiemSinhVien();
                    objectDiemSinhVien.setId(i);
                    objectDiemSinhVien.setMaSV(rs.getString(1));
                    objectDiemSinhVien.setDiemCC(rs.getFloat(2));
                    objectDiemSinhVien.setDiemGK(rs.getFloat(3));
                    objectDiemSinhVien.setDiemCK(rs.getFloat(4));
                    objectDiemSinhVien.setDiemTK(rs.getFloat(5));

                    arrDiemSV.add(objectDiemSinhVien);
                }
                Toast.makeText(context, NhapDiem.maLTC_NhapDiem+"", Toast.LENGTH_SHORT).show();

                connect.close();
            }
            else{
                connectionResult="Check Connection";
            }
        }
        catch (Exception e){
            Log.e("",e.getMessage());
        }
    }

    private void setControl(){
        lvDSSV_LTC = (ListView) findViewById(R.id.lvDSSV_LTC);
        maLTC=findViewById(R.id.maLTC);
        tenMH=findViewById(R.id.tenMH);
        searchSV = findViewById(R.id.searchTaiKhoan);
        btnClickback = findViewById(R.id.btnClickback);
        arrDiemSV = new ArrayList<>();

    }
    private void setEvent(){
        maLTC.setText(NhapDiem.maLTC_NhapDiem);
        tenMH.setText(NhapDiem.tenMH_NhapDiem);
        getDSSV();
    }
    public void timKiemMaSV(String kitu){
        try {
            connect = DatabaseManager.getConnection();
            String query =
                    "SELECT SV.MaSV, DK.DIEMCC,DK.DiemGK,DK.DiemCK,(MH.HeSoCC*DK.DiemCC + MH.HeSoGK*DK.DiemGK + MH.HeSocK*DK.DiemGK)/100 AS DIEMTK  \n" +
                            "FROM LOPTINCHI LTC, SINHVIEN SV,MONHOC MH, DangKi DK\n" +
                            "WHERE DK.MaLTC='"+ NhapDiem.maLTC_NhapDiem+"' AND DK.MaSV=SV.MaSV AND LTC.MaMH=MH.MaMH and DK.MALTC = LTC.MALTC";

            if (connect != null) {
                query = query + " and (SV.MaSV like N'%"+kitu+"%')";
                arrDiemSV.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    objectDiemSinhVien = new DiemSinhVien();
                    objectDiemSinhVien.setId(i);
                    objectDiemSinhVien.setMaSV(rs.getString(1));
                    objectDiemSinhVien.setDiemCC(rs.getFloat(2));
                    objectDiemSinhVien.setDiemGK(rs.getFloat(3));
                    objectDiemSinhVien.setDiemCK(rs.getFloat(4));
                    objectDiemSinhVien.setDiemTK(rs.getFloat(5));

                    arrDiemSV.add(objectDiemSinhVien);
                }
                connect.close();
            }
            else{
                connectionResult="Check Connection";
            }
        }
        catch (Exception e){
            Log.e("",e.getMessage());
        }
    }

    public void searchTaiKhoan() {
        searchSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                {
                    getDSSV();
                    diemSinhVienAdapter.notifyDataSetChanged();
                }
                else
                {
                    timKiemMaSV(newText);
                    diemSinhVienAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }


}