package com.example.QLDSV;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.Database.DatabaseManager;
import com.example.adapter.DangKyLopTinChiAdapter;
import com.example.adapter.LopTinChiDaDangKyAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DangKyLopTinChi extends AppCompatActivity {

    Connection connect;
    String connectionResult="";
    Spinner cbNienKhoa, cbHocKy;
    ArrayList<String> arrNienKhoa = new ArrayList<String>();
    ArrayList<String> arrHocKy = new ArrayList<String>();
    String  MaSinhVien;
    ListView listviewDK;
    ArrayList<com.example.Objects.DangKyLopTinChi> arrDangKyLopTinChi;
    com.example.Objects.DangKyLopTinChi objectDangKyLopTinChi;
    DangKyLopTinChiAdapter dangKyLopTinChiAdapter;
    LopTinChiDaDangKyAdapter lopTinChiDaDangKyAdapter;
    int vitri=-1,vitriNienKhoa=0, vitriHocKy=0;
    Button nutLuuDangKy, nutTroVe;
    Context context;
    SearchView searchView = null;
    public static ProgressBar thanhPG;

    public static ObjectAnimator animation = ObjectAnimator.ofInt(thanhPG, "progress", 0, 100);
    public static String queryUpdateDangKyLTC="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_lop_tin_chi);
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

        //Hàm trạng thái tiến trình
        thanhPG.setVisibility(View.INVISIBLE);

        animation.setDuration(2000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                thanhPG.setVisibility(View.INVISIBLE);
                nutLuuDangKy.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });

        ArrayAdapter arrayAdapterNienKhoa = new ArrayAdapter(DangKyLopTinChi.this,android.R.layout.simple_spinner_item,arrNienKhoa);
        cbNienKhoa.setAdapter(arrayAdapterNienKhoa);
        arrayAdapterNienKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdapterHocKy = new ArrayAdapter(DangKyLopTinChi.this,android.R.layout.simple_spinner_item,arrHocKy);
        cbHocKy.setAdapter(arrayAdapterHocKy);
        arrayAdapterHocKy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dangKyLopTinChiAdapter = new DangKyLopTinChiAdapter(context,arrDangKyLopTinChi);
        listviewDK.setAdapter(dangKyLopTinChiAdapter);
        listviewDK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DangKyLopTinChi.this, "List item " + position, Toast.LENGTH_SHORT).show();
            }
        });

        cbNienKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(context, arrNienKhoa.get(i), Toast.LENGTH_SHORT).show();
                vitriNienKhoa=i;
                xuatDSDKLTC();
                dangKyLopTinChiAdapter.notifyDataSetChanged();
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
//                Toast.makeText(context, arrHocKy.get(i), Toast.LENGTH_SHORT).show();
                vitriHocKy=i;
                xuatDSDKLTC();
                dangKyLopTinChiAdapter.notifyDataSetChanged();
                searchView.setQuery("", false);
                searchView.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nutLuuDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongBaoLuu(DangKyLopTinChi.this,view);
            }
        });
        nutTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LopTinChiDaDangKy.class);
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
        SearchManager searchManager = (SearchManager) DangKyLopTinChi.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(DangKyLopTinChi.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Bắt kí tự
            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(LopTinChiDaDangKy.this, searchView.getQuery(), Toast.LENGTH_SHORT).show();

                if (searchView.getQuery().toString().isEmpty())
                {
                    xuatDSDKLTC();
                    dangKyLopTinChiAdapter.notifyDataSetChanged();
                }
                else
                {
                    cbHocKy.setSelection(0);
                    cbNienKhoa.setSelection(0);
                    timKiemDK(searchView.getQuery().toString());
                    dangKyLopTinChiAdapter.notifyDataSetChanged();
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
        cbNienKhoa=(Spinner) findViewById(R.id.CbNienKhoaDKLTC);
        cbHocKy=(Spinner) findViewById(R.id.CbHocKyDKLTC);
        listviewDK = (ListView) findViewById(R.id.listviewDK);
        arrDangKyLopTinChi = new ArrayList<>();
        nutLuuDangKy = (Button) findViewById(R.id.buttonLuuDangKy);
        thanhPG = (ProgressBar) findViewById(R.id.progressBar);
        nutTroVe = (Button) findViewById(R.id.huy_dkltc);
    }

    private void AddItem() {
        arrHocKy.add("All");
        arrHocKy.add("HK1");
        arrHocKy.add("HK2");

        xuatNienKhoa();
        xuatDSDKLTC();
    }

    public void xuatDSDKLTC()
    {
        try
        {
            connect = DatabaseManager.getConnection();
            if (connect != null) {
                String query = "";

                if(arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
                    query = "select ltc.maltc, ltc.MaMH, SLToiDa,\n" +
                            "slconlai= slconlai - count (case when dk.MaSV=sv.MaSV and dk.MaLTC=ltc.MaLTC then (1) else null end),\n" +
                            "ltc.NgayBD, ltc.NgayKT\n" +
                            "from LopTinChi ltc\n" +
                            "inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "inner join SinhVien sv on sv.MaSV=dk.MaSV \n" +
                            "where ltc.TrangThai='1'\n" +
                            "group by ltc.maltc,ltc.mamh, ltc.sltoida, ltc.SLConLai, ltc.NgayBD, ltc.NgayKT\n" +
                            "order by cast(substring(ltc.MaLTC,4,10) as int)";
                else if (arrNienKhoa.get(vitriNienKhoa).equals("All") && !arrHocKy.get(vitriHocKy).equals("All"))
                    query = "select ltc.maltc, ltc.MaMH, SLToiDa,\n" +
                            "slconlai= slconlai - count (case when dk.MaSV=sv.MaSV and dk.MaLTC=ltc.MaLTC then (1) else null end),\n" +
                            "ltc.NgayBD, ltc.NgayKT\n" +
                            "from LopTinChi ltc\n" +
                            "inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "inner join SinhVien sv on sv.MaSV=dk.MaSV \n" +
                            "where ltc.TrangThai='1' and ltc.hocki='"+arrHocKy.get(vitriHocKy)+"'\n" +
                            "group by ltc.maltc,ltc.mamh, ltc.sltoida, ltc.SLConLai, ltc.NgayBD, ltc.NgayKT\n" +
                            "order by cast(substring(ltc.MaLTC,4,10) as int)";

                else if (!arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
                    query = "select ltc.maltc, ltc.MaMH, SLToiDa,\n" +
                            "slconlai= slconlai - count (case when dk.MaSV=sv.MaSV and dk.MaLTC=ltc.MaLTC then (1) else null end),\n" +
                            "ltc.NgayBD, ltc.NgayKT\n" +
                            "from LopTinChi ltc\n" +
                            "inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "inner join SinhVien sv on sv.MaSV=dk.MaSV \n" +
                            "where ltc.TrangThai='1' and ltc.namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"'\n" +
                            "group by ltc.maltc,ltc.mamh, ltc.sltoida, ltc.SLConLai, ltc.NgayBD, ltc.NgayKT\n" +
                            "order by cast(substring(ltc.MaLTC,4,10) as int)";

                else
                    query = "select ltc.maltc, ltc.MaMH, SLToiDa,\n" +
                            "slconlai= slconlai - count (case when dk.MaSV=sv.MaSV and dk.MaLTC=ltc.MaLTC then (1) else null end),\n" +
                            "ltc.NgayBD, ltc.NgayKT\n" +
                            "from LopTinChi ltc\n" +
                            "inner join DangKi dk on dk.MaLTC=ltc.MaLTC\n" +
                            "inner join SinhVien sv on sv.MaSV=dk.MaSV \n" +
                            "where ltc.TrangThai='1' and ltc.hocki='"+arrHocKy.get(vitriHocKy)+"' and ltc.namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"'\n" +
                            "group by ltc.maltc,ltc.mamh, ltc.sltoida, ltc.SLConLai, ltc.NgayBD, ltc.NgayKT\n" +
                            "order by cast(substring(ltc.MaLTC,4,10) as int)";

                arrDangKyLopTinChi.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    objectDangKyLopTinChi = new com.example.Objects.DangKyLopTinChi();
                    objectDangKyLopTinChi.setId(i);
                    objectDangKyLopTinChi.setMaLTC(rs.getString(1));
                    objectDangKyLopTinChi.setTenMH(searchTenMonHoc(rs.getString(2)));
                    objectDangKyLopTinChi.setSlTD(rs.getInt(3));
                    objectDangKyLopTinChi.setSlCL(rs.getInt(4));
                    objectDangKyLopTinChi.setNgayBD(rs.getString(5));
                    objectDangKyLopTinChi.setNgayKT(String.valueOf(rs.getDate(6)));

                    objectDangKyLopTinChi.setIschecked(false);
                    for (int j = 0; j< LopTinChiDaDangKy.arrLopTinChiDaDangKy.size(); j++)
                    {
                        if (LopTinChiDaDangKy.arrLopTinChiDaDangKy.get(j).getMaLTC().equals(objectDangKyLopTinChi.getMaLTC()))
                        {
                            objectDangKyLopTinChi.setIschecked(true);
                        }
                    }
                    arrDangKyLopTinChi.add(objectDangKyLopTinChi);
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


    public void UpdateDangKiLTC() {
        try
        {
            connect = DatabaseManager.getConnection();
            if (connect != null) {
                String query = queryUpdateDangKyLTC;
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                connect.close();
            }
        }
        catch (Exception e){
            // LTC... đã hết slot òi
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
                        "where ltc.TrangThai='1' and (ltc.maltc like N'%"+kitu+"%' or mh.TenMH like N'%"+kitu+"%')";
                arrDangKyLopTinChi.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    objectDangKyLopTinChi = new com.example.Objects.DangKyLopTinChi();
                    objectDangKyLopTinChi.setId(i);
                    objectDangKyLopTinChi.setMaLTC(rs.getString(1));
                    objectDangKyLopTinChi.setTenMH(searchTenMonHoc(rs.getString(8)));
                    objectDangKyLopTinChi.setSlTD(rs.getInt(5));
                    objectDangKyLopTinChi.setSlCL(rs.getInt(4));
                    objectDangKyLopTinChi.setNgayBD(rs.getString(6));
                    objectDangKyLopTinChi.setNgayKT(String.valueOf(rs.getDate(7)));

                    objectDangKyLopTinChi.setIschecked(false);
                    for (int j = 0; j< LopTinChiDaDangKy.arrLopTinChiDaDangKy.size(); j++)
                    {
                        if (LopTinChiDaDangKy.arrLopTinChiDaDangKy.get(j).getMaLTC().equals(objectDangKyLopTinChi.getMaLTC()))
                        {
                            objectDangKyLopTinChi.setIschecked(true);
                        }
                    }
                    arrDangKyLopTinChi.add(objectDangKyLopTinChi);
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

    public void ThongBaoLuu(Context context, View v){
        AlertDialog.Builder thongbao = new AlertDialog.Builder(context);
        thongbao.setTitle("Thông báo!");
        thongbao.setIcon(R.drawable.ic_thongbao);
        thongbao.setMessage("Bạn có muốn lưu không?");

        thongbao.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {


                nutLuuDangKy.setVisibility(View.INVISIBLE);
                thanhPG.setVisibility(View.VISIBLE);
                animation.start();
                //Chạy hàm lưu, sau đó cho biến = null.
                // Đợi 2  giây để thực hiện:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Log.d("upd", queryUpdateDangKyLTC);
                        UpdateDangKiLTC();
                        queryUpdateDangKyLTC="";
                        Intent intent = new Intent(v.getContext(), LopTinChiDaDangKy.class);

                        intent.putExtra("maSinhVien", MaSinhVien);

                        v.getContext().startActivity(intent);
                    }
                }, 2000);

            }
        });
        thongbao.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        thongbao.show();
    }


}