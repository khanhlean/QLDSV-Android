package com.example.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.QLDSV.LopTinChiDaDangKy;
import com.example.Objects.DangKyLopTinChi;
import com.example.QLDSV.R;

import java.util.ArrayList;

public class DangKyLopTinChiAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<DangKyLopTinChi> listDKLTC;
    private DangKyLopTinChi objectDangKyLopTinChi;
    public static ArrayList<Integer> dsLTCduocchon = new ArrayList<Integer>();


    public DangKyLopTinChiAdapter(Context context, ArrayList<DangKyLopTinChi> listDKLTC) {
        this.context = context;
        this.listDKLTC = listDKLTC;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listDKLTC.size();
    }

    @Override
    public Object getItem(int i) {
        return listDKLTC.get(i);
    }


    public long getItemId(int i) {
        return listDKLTC.get(i).getId();
    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {

        View rowView=view;
        if (rowView==null) {
            rowView=layoutInflater.inflate(R.layout.item_dangkyloptinchi,parent,false);
        }

        // Ánh xạ view
        TextView maLTC = (TextView) rowView.findViewById(R.id.textviewMa_DKLTC);
        TextView tenMH = (TextView) rowView.findViewById(R.id.textviewTen_DKLTC);
        TextView slTD = (TextView) rowView.findViewById(R.id.textviewSLTD_DKLTC);
        TextView slCL = (TextView) rowView.findViewById(R.id.textviewSLCL_DKLTC);
        TextView ngayBD = (TextView) rowView.findViewById(R.id.labelNgayBD_DKLTC);
        TextView ngayKT = (TextView) rowView.findViewById(R.id.textviewNgayKT_DKLTC);
        CheckBox hopCheckDKLTC = (CheckBox) rowView.findViewById(R.id.checkbox_DKLTC);


        // Gán giá trị
        objectDangKyLopTinChi =listDKLTC.get(i);
        maLTC.setText(objectDangKyLopTinChi.getMaLTC());
        tenMH.setText(objectDangKyLopTinChi.getTenMH());
        slTD.setText(String.valueOf(objectDangKyLopTinChi.getSlTD()));
        slCL.setText(String.valueOf(objectDangKyLopTinChi.getSlCL()));
        ngayBD.setText(objectDangKyLopTinChi.getNgayBD());
        ngayKT.setText(objectDangKyLopTinChi.getNgayKT());
        hopCheckDKLTC.setChecked(objectDangKyLopTinChi.isIschecked());
        hopCheckDKLTC.setTag(i);

        //Toast.makeText(context, "MonHoc da them: " + ThemChuyenNganh.arrMonHoc.size() + "\nDanh sach cac mon hoc: " + KeHoach.arrMonHoc.size(), Toast.LENGTH_SHORT).show();

        hopCheckDKLTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = (int) v.getTag();
                boolean isChecked = false;
                if (listDKLTC.get(currentPos).isIschecked()==false){
                    if(listDKLTC.get(currentPos).getSlCL()==0)
                    {
                        AlertDialog.Builder thongbao = new AlertDialog.Builder((com.example.QLDSV.DangKyLopTinChi) v.getContext());
                        thongbao.setTitle("Thông báo!");
                        thongbao.setMessage(listDKLTC.get(currentPos).getMaLTC() + " đã hết slot.");
                        thongbao.setIcon(R.drawable.ic_thongbao);

                        thongbao.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }
                        );
                        thongbao.show();
                    }

                    else {
                        isChecked=true;
                        //Toast.makeText(context, "Chon:" +currentPos, Toast.LENGTH_SHORT).show();
                        dsLTCduocchon.add(Integer.valueOf(currentPos));

                        //Toast.makeText(context, "Chon:" + listDKLTC.get(currentPos).getMaLTC(), Toast.LENGTH_SHORT).show();
                        com.example.QLDSV.DangKyLopTinChi.queryUpdateDangKyLTC+="insert into dangki(MaLTC,MaSV) values ('"+listDKLTC.get(currentPos).getMaLTC()+"','"+ LopTinChiDaDangKy.MaSinhVien +"');\n";
                    }
                }
                else
                {
                    //Toast.makeText(context, "HuyChon: "+currentPos, Toast.LENGTH_SHORT).show();
                    com.example.QLDSV.DangKyLopTinChi.queryUpdateDangKyLTC+="delete from dangki where MaLTC='"+listDKLTC.get(currentPos).getMaLTC()+"' and MaSV='"+ LopTinChiDaDangKy.MaSinhVien +"';\n";
                    dsLTCduocchon.remove(Integer.valueOf(currentPos));
                }

                listDKLTC.get(currentPos).setIschecked(isChecked);
                notifyDataSetChanged();
            }
        });


        // Đổi màu items
        rowView.setBackgroundResource(R.drawable.item_dangky);
        return rowView;
    }

    public void ThongBaoHetSlot(Context context, View v){
        AlertDialog.Builder thongbao = new AlertDialog.Builder(context);
        thongbao.setTitle("Thông báo!");
        thongbao.setIcon(R.drawable.ic_thongbao);
        thongbao.setMessage("Lớp tín chỉ bạn chọn đã hết slot!");

//        thongbao.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//
//            }
//        });
        thongbao.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

}
