package com.example.Objects;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBDiemSV extends SQLiteOpenHelper {

    public DBDiemSV(@Nullable Context context) {
        super(context, "tbDiemSV", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table tbDiemSV (id INTEGER, MaLTC text, MaSV text, MaSV text, DiemCC REAL, DiemGK REAL, DiemCK REAL, DiemTK REAL, Huy INTEGER )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void ThemDL(DiemSinhVien diem){
        SQLiteDatabase sqliteDB = getWritableDatabase();
        String sql = "insert into tbDiemSV values(?,?,?)";
        sqliteDB.execSQL(sql, new Object[]{diem.getId(), diem.getMaLTC(), diem.getMaSV(), diem.getDiemCC(),
        diem.getDiemGK(), diem.getDiemGK(), diem.getDiemCK(), diem.getDiemTK(), diem.isHuy()});
    }

    public ArrayList<DiemSinhVien> HienThiDL(){
        ArrayList<DiemSinhVien> data = new ArrayList<>();

        String sql = "select * from tbDiemSV";
        SQLiteDatabase sqliteDB = getReadableDatabase();

        Cursor cursor = sqliteDB.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                DiemSinhVien diem = new DiemSinhVien();
                diem.setId((cursor.getInt(0)));
                diem.setMaLTC((cursor.getString(1)));
                diem.setMaSV((cursor.getString(2)));
                diem.setDiemCC((cursor.getFloat(3)));
                diem.setDiemGK((cursor.getFloat(4)));
                diem.setDiemCK((cursor.getFloat(5)));
                diem.setDiemTK((cursor.getFloat(6)));
                diem.setHuy(Boolean.parseBoolean(Integer.toString(cursor.getInt(7))));
                data.add(diem);
            }while (cursor.moveToNext());
        }
        return data;
    }

    public void SuaDL(DiemSinhVien diem){
        SQLiteDatabase sqliteDB = getWritableDatabase();
        String sql = "Update tbDiemSV set DiemCC=?, DiemGK=?,DiemCK=? where id=?";
        sqliteDB.execSQL(sql, new Object[]{diem.getDiemCC(), diem.getDiemGK(), diem.getDiemCK(), diem.getId()});
    }

//    public void XoaDL(SanPham sp){
//        SQLiteDatabase sqliteDB = getWritableDatabase();
//        String sql = "Delete from tbDiemSV where masp=?";
//        sqliteDB.execSQL(sql, new String[]{sp.getMaSP()});
//    }
}
