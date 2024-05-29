package com.example.QLDSV;

import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.Database.DatabaseManager;
import com.example.adapter.LopTinChiDaDangKyAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LopTinChiDaDangKy extends AppCompatActivity {

    Connection connect;
    String connectionResult="";
    Spinner cbNienKhoa, cbHocKy;
    ArrayList<String> arrNienKhoa = new ArrayList<String>();
    ArrayList<String> arrHocKy = new ArrayList<String>();

    ListView listviewDK;
    public static ArrayList<com.example.Objects.LopTinChiDaDangKy> arrLopTinChiDaDangKy;
    com.example.Objects.LopTinChiDaDangKy objectLopTinChiDaDangKy;
    LopTinChiDaDangKyAdapter lopTinChiDaDangKyAdapter;
    int vitri=-1, vitriNienKhoa=0, vitriHocKy=0;
    Button nutCapNhatDangKy, nutTroVe;

    Context context;
    SearchView searchView = null;

    public static ProgressBar thanhPG;

    public static ObjectAnimator animation = ObjectAnimator.ofInt(thanhPG, "progress", 0, 100);

    public static String MaSinhVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop_tin_chi_da_dang_ky);
        context=this;
        Intent intent = getIntent();
        MaSinhVien = intent.getStringExtra("maSinhVien");

        //setTheme(android.R.style.Theme_Holo);
//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#0E407D"));
//        actionBar.setBackgroundDrawable(colorDrawable);

        AnhXa();
        AddItem();

        ArrayAdapter arrayAdapterNienKhoa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrNienKhoa);
        cbNienKhoa.setAdapter(arrayAdapterNienKhoa);
        arrayAdapterNienKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterHocKy = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrHocKy);
        cbHocKy.setAdapter(arrayAdapterHocKy);
        arrayAdapterHocKy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //lopTinChiDaDangKyAdapter = new LopTinChiDaDangKyAdapter(context,arrLopTinChiDaDangKy);
        lopTinChiDaDangKyAdapter = new LopTinChiDaDangKyAdapter(this,R.layout.item_loptinchidadangky,arrLopTinChiDaDangKy);
        listviewDK.setAdapter(lopTinChiDaDangKyAdapter);
        listviewDK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LopTinChiDaDangKy.this, "Item: "+ com.example.QLDSV.LopTinChiDaDangKy.arrLopTinChiDaDangKy.get(position).getMaLTC(), Toast.LENGTH_SHORT).show();
            }
        });

        cbNienKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitriNienKhoa=i;
                xuatDSLTCDDK();
                lopTinChiDaDangKyAdapter.notifyDataSetChanged();
                searchView.setQuery("", false);
                searchView.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cbHocKy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitriHocKy=i;
                xuatDSLTCDDK();
                lopTinChiDaDangKyAdapter.notifyDataSetChanged();
                searchView.setQuery("", false);
                searchView.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nutCapNhatDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for(int i=0; i<DangKyLopTinChiAdapter.dsLTCduocchon.size(); i++)
//                    Toast.makeText(context,arrLopTinChiDaDangKy.get(Integer.valueOf(LopTinChiDaDangKyAdapter.dsLTCduocchon.get(i))).getMaLTC(), Toast.LENGTH_SHORT).show();

                nutCapNhatDangKy.setVisibility(View.INVISIBLE);
                thanhPG.setVisibility(View.VISIBLE);
                animation.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        cbNienKhoa.setSelection(0);
                        cbHocKy.setSelection(0);

                        xuatDSLTCDDK();
                        Intent intent = new Intent(view.getContext(), DangKyLopTinChi.class);
                        intent.putExtra("maSinhVien", MaSinhVien);
                        startActivity(intent);
                        thanhPG.setVisibility(View.INVISIBLE);
                        nutCapNhatDangKy.setVisibility(View.VISIBLE);
                    }
                }, 2000);   //2 seconds
            }
        });
        nutTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TrangChuSV.class);
                intent.putExtra("maSinhVien", MaSinhVien);
                view.getContext().startActivity(intent);
            }
        });
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dangky, menu);

        //Nút Tìm kiếm
        MenuItem searchItem = menu.findItem(R.id.menuTimkiem);
        SearchManager searchManager = (SearchManager) LopTinChiDaDangKy.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(LopTinChiDaDangKy.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Bắt kí tự
            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(LopTinChiDaDangKy.this, searchView.getQuery(), Toast.LENGTH_SHORT).show();

                if (searchView.getQuery().toString().isEmpty())
                {
                    xuatDSLTCDDK();
                    lopTinChiDaDangKyAdapter.notifyDataSetChanged();
                }
                else
                {
                    cbHocKy.setSelection(0);
                    cbNienKhoa.setSelection(0);
                    timKiemDK(searchView.getQuery().toString());
                    lopTinChiDaDangKyAdapter.notifyDataSetChanged();
                }
                return false;
            }
            //Bắt kí tự sau khi enter (không enter được khi null)
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void AnhXa() {
        cbNienKhoa=(Spinner) findViewById(R.id.CbNienKhoa);
        cbHocKy=(Spinner) findViewById(R.id.CbHocKy);
        listviewDK = (ListView) findViewById(R.id.listviewLTCDDK);
        arrLopTinChiDaDangKy = new ArrayList<>();
        nutCapNhatDangKy = (Button) findViewById(R.id.buttonCapNhatDangKy);
        thanhPG = (ProgressBar) findViewById(R.id.progressBar);
        nutTroVe = (Button) findViewById(R.id.trove_ltcddk);
    }

    private void AddItem() {
        arrHocKy.add("All");
        arrHocKy.add("HK1");
        arrHocKy.add("HK2");
        xuatNienKhoa();
        xuatDSLTCDDK();
    }

    public void xuatDSLTCDDK()
    {
        try
        {
            connect = DatabaseManager.getConnection();
            if (connect != null) {
                String query="";
                if(arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
                    query = "select * from LopTinChi ltc inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "where dk.MaSV = '"+MaSinhVien+"' order by cast(substring(dk.MaLTC,4,10) as int)";
                else if (arrNienKhoa.get(vitriNienKhoa).equals("All") && !arrHocKy.get(vitriHocKy).equals("All"))
                    query = "select * from LopTinChi ltc inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "where dk.MaSV = '"+MaSinhVien+"' and hocki='"+arrHocKy.get(vitriHocKy)+"' order by cast(substring(dk.MaLTC,4,10) as int)";
                else if (!arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
                    query = "select * from LopTinChi ltc inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "where dk.MaSV = '"+MaSinhVien+"' and namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"' order by cast(substring(dk.MaLTC,4,10) as int)";
                else
                    query = "select * from LopTinChi ltc inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            " where dk.MaSV = '"+MaSinhVien+"' and hocki='"+arrHocKy.get(vitriHocKy)+"' and namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"' order by cast(substring(dk.MaLTC,4,10) as int)";

                arrLopTinChiDaDangKy.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    objectLopTinChiDaDangKy = new com.example.Objects.LopTinChiDaDangKy();
                    objectLopTinChiDaDangKy.setId(i);
                    objectLopTinChiDaDangKy.setMaLTC(rs.getString(1));
                    objectLopTinChiDaDangKy.setTenMH(searchTenMonHoc(rs.getString(8)));
                    objectLopTinChiDaDangKy.setNienKhoa(rs.getString(2));
                    objectLopTinChiDaDangKy.setHocKy(rs.getString(3));
                    objectLopTinChiDaDangKy.setNgayBD(rs.getString(6));
                    objectLopTinChiDaDangKy.setNgayKT(String.valueOf(rs.getDate(7)));

                    arrLopTinChiDaDangKy.add(objectLopTinChiDaDangKy);
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

    public void xuatNienKhoa() {
        try
        {
            connect = DatabaseManager.getConnection();
            if (connect != null) {
                String query = "select distinct namhoc, cast(substring(NamHoc,0,5) as int)\n" +
                        "from LopTinChi\n" +
                        "group by namhoc\n" +
                        "order by cast(substring(NamHoc,0,5) as int) DESC, NamHoc";
                arrNienKhoa.clear();
                arrNienKhoa.add("All");
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    arrNienKhoa.add(rs.getString(1));
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

    public String searchTenMonHoc(String maMH) {
        String sql = " SELECT tenmh FROM monhoc WHERE mamh=N'" + maMH + "'";
        try {
            connect = DatabaseManager.getConnection();
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    public void timKiemDK(String kitu){
        try {
            connect = DatabaseManager.getConnection();
            if (connect != null) {
                String query = "select * from LopTinChi ltc\n" +
                        "inner join MonHoc mh on ltc.MaMH=mh.MaMH\n" +
                        "inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                        "where dk.MaSV = '"+MaSinhVien+"' and (ltc.maltc like N'%"+kitu+"%' or mh.TenMH like N'%"+kitu+"%')";
                arrLopTinChiDaDangKy.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    objectLopTinChiDaDangKy = new com.example.Objects.LopTinChiDaDangKy();
                    objectLopTinChiDaDangKy.setId(i);
                    objectLopTinChiDaDangKy.setMaLTC(rs.getString(1));
                    objectLopTinChiDaDangKy.setTenMH(searchTenMonHoc(rs.getString(8)));
                    objectLopTinChiDaDangKy.setNienKhoa(rs.getString(2));
                    objectLopTinChiDaDangKy.setHocKy(rs.getString(3));
                    objectLopTinChiDaDangKy.setNgayBD(rs.getString(6));
                    objectLopTinChiDaDangKy.setNgayKT(String.valueOf(rs.getDate(7)));

                    arrLopTinChiDaDangKy.add(objectLopTinChiDaDangKy);
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
}