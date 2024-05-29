package com.example.QLDSV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.Database.DatabaseManager;
import com.example.adapter.ImagePagerAdapter;
import android.os.Handler;

import com.example.adapter.ChartPagerAdapter;

import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import java.sql.PreparedStatement;

import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class TrangChuSV extends AppCompatActivity {

    private boolean isBannerVisible = true;
    private final Handler mHandler = new Handler();
    private ViewPager viewPagerHinh,viewPagerChart;
    private View bannerView;
    private Button closeButton,viewtbbtn ;
    private TextView tvName, banner_thongbao_tv , sothongbao_tv ;
    private CardView cvAnhThe ,cvNoti, cvXemDiem, cvDangKy,cvAcc;
    private ImageButton menubtn,btnUser;
    private DrawerLayout drawerLayoutTrangChu;
    String maSinhVien = "";
    String matKhau = "";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchusinhvien);
        Intent intent = getIntent();
        maSinhVien = intent.getStringExtra("maSinhVien");
        matKhau = intent.getStringExtra("matKhau");
//      Ánh xạ
        setControl();

//      Giao diện
        hienThi();

//      Click
        setEvent();

    }
    private void setEvent() {
        cvNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuSV.this, ThongBaoActivity.class);
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBannerVisible) {
                    bannerView.setVisibility(View.GONE);
                    isBannerVisible = false;
                }
            }
        });
        viewtbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuSV.this, ThongBaoActivity.class);
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });
        cvXemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuSV.this, ResultViewActivity.class);
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });

        cvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuSV.this, LopTinChiDaDangKy.class);
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });
        cvAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuSV.this, ThongTinSinhVien.class);
                intent.putExtra("maSinhVien", maSinhVien);
                intent.putExtra("matKhau", matKhau);
                startActivity(intent);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuSV.this, ThongTinSinhVien.class);
                intent.putExtra("maSinhVien", maSinhVien);
                intent.putExtra("matKhau", matKhau);
                startActivity(intent);
            }
        });
        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutTrangChu.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.

                switch (item.getItemId()){
                    case R.id.nav_xemdiem: {
                        Intent intent = new Intent(TrangChuSV.this, ResultViewActivity.class);
                        intent.putExtra("maSinhVien", maSinhVien);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_dkmon: {
                        Intent intent = new Intent(TrangChuSV.this, LopTinChiDaDangKy.class);
                        intent.putExtra("maSinhVien", maSinhVien);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_taikhoan: {
                        Intent intent = new Intent(TrangChuSV.this, ThongTinSinhVien.class);
                        intent.putExtra("maSinhVien", maSinhVien);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_logout: {
                        Intent intent = new Intent(TrangChuSV.this, DangNhap.class);
                        startActivity(intent);
                        break;
                    }
                }

                drawerLayoutTrangChu.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void hienThi(){
        viewpageimgptit();
        imghinhsvbtn();
        thongbao();
        hientensv();
        //      Adapter
        viewPagerChart.setAdapter(new ChartPagerAdapter(getSupportFragmentManager()));
    }
    private void setControl() {
        bannerView  =(View)         findViewById(R.id.banner_noti_layout);

        closeButton =(Button)       findViewById(R.id.close_button);
        viewtbbtn   =(Button)       findViewById(R.id.view_thongbao_btn);

        menubtn   =(ImageButton)  findViewById(R.id.menubtn);
        btnUser=(ImageButton)  findViewById(R.id.btnUser);

        drawerLayoutTrangChu= findViewById(R.id.drawer_layout_trangchu);

        cvAnhThe    =(CardView)     findViewById(R.id.img_sv_cv);


        cvNoti      =(CardView)     findViewById(R.id.btnNotice);
        cvXemDiem =(CardView)     findViewById(R.id.cv_xemdiem);
        cvDangKy=(CardView)     findViewById(R.id.cv_dangkymon);
        cvAcc=(CardView)     findViewById(R.id.cv_acc);

        tvName=(TextView)    findViewById(R.id.tvName);
        banner_thongbao_tv = findViewById(R.id.banner_thongbao_tv);
        sothongbao_tv = findViewById(R.id.notification_count);

        viewPagerChart   =(ViewPager)    findViewById(R.id.chart_layout);

        imageView = findViewById(R.id.img_sv);


        viewPagerHinh = findViewById(R.id.img_viewpager);




    }

    void viewpageimgptit(){

            ImagePagerAdapter adapter = new ImagePagerAdapter(this, new int[] { R.drawable.ptithcm, R.drawable.ptithcm1, R.drawable.ptithcm2 });
            viewPagerHinh.setAdapter(adapter);
            mHandler.post(mRunnable);
            viewPagerHinh.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            ViewPager viewPager = findViewById(R.id.img_viewpager);
            int currentItem = viewPager.getCurrentItem() ;
            int totalItems = viewPager.getAdapter().getCount();
            int nextItem = (currentItem + 1) % totalItems;
            viewPager.setCurrentItem(nextItem);
            mHandler.postDelayed(mRunnable, 5000);
        }
    };
    void imghinhsvbtn(){
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT HinhAnh FROM SinhVien WHERE MaSV  = '"+maSinhVien+"'";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String imagePath = rs.getString("HinhAnh");
                Log.d("TAG", "imghinhsvbtn: "+imagePath);
                Picasso.get().load(imagePath).into(imageView);

//                Glide.with(imageView.getContext())
//                        .load(imagePath)
//                        .into(imageView);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void thongbao(){
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "SELECT COUNT(*) AS SoLuongChuaDoc FROM SinhVien_ThongBao WHERE MaSV = '"+maSinhVien+"' AND DaDoc = 0 ";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            int soLuongChuaDoc = 0;

            while (rs.next()) {
                soLuongChuaDoc = rs.getInt("SoLuongChuaDoc");

                if (soLuongChuaDoc > 0){

//                System.out.println(soLuongChuaDoc);
                banner_thongbao_tv.setText(soLuongChuaDoc+ " thông báo mới!");
                sothongbao_tv.setText(Integer.toString(soLuongChuaDoc));

                }else {
                    sothongbao_tv.setBackgroundResource(0);
                    sothongbao_tv.setText("");
                    if (isBannerVisible) {
                        bannerView.setVisibility(View.GONE);
                        isBannerVisible = false;
                    }
                }
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void hientensv(){
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT HoTen FROM SinhVien WHERE MaSV  = '"+maSinhVien+"'";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String hoTenSV = rs.getString("HoTen");
                tvName.setText(hoTenSV);

            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}