package com.example.QLDSV;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CapNhatDiem extends AppCompatActivity {

    TextView maLTC, tenMH, maSV, tenSV;
    EditText diemCC, diemGK, diemCK;

    Button btnUpdate;

    Connection conn;
    String connectionResult="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_diem);

        setControl();
        setEvent();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Kiểm tra điểm có hợp lệ hay không
                if(!checkDiem(diemCC)) diemCC.setError("Điểm chuyên cần không hợp lệ!");
                else if(!checkDiem(diemGK)) diemGK.setError("Điểm giữa kì không hợp lệ!");
                else if(!checkDiem(diemCK)) diemCK.setError("Điểm cuối kì không hợp lệ!");
                else {
                    Float diemCC_Update,diemGK_Update,diemCK_Update;
                    diemCC_Update = Float.parseFloat(diemCC.getText().toString());
                    diemGK_Update = Float.parseFloat(diemGK.getText().toString());
                    diemCK_Update = Float.parseFloat(diemCK.getText().toString());
                    try {
                        conn = DatabaseManager.getConnection();
                        if (conn != null) {
                            String query = "UPDATE DangKi\n" +
                                    "SET DIEMCC = " + diemCC_Update + " , DiemGK = " + diemGK_Update + " , DiemCK =" + diemCK_Update + "\n" +
                                    "WHERE MaLTC='" + NhapDiem_CT_LTC.maLTC_ct_ltc + "' AND MaSV='" + NhapDiem_CT_LTC.maSV_ct_ltc + "'";
                            Statement st = conn.createStatement();
                            st.executeUpdate(query);

                            conn.close();
                            alertSuccess("Cập nhật điểm cho sinh viên: " + NhapDiem_CT_LTC.maSV_ct_ltc + " thành công! ");

                        } else {
                            connectionResult = "Check Connection";
                        }
                    } catch (Exception e) {
                        Log.e("", e.getMessage());
                    }
                }
            }
        });
    }
    private boolean checkDiem(EditText txt){
        Float diem;
        try {
            diem = Float.parseFloat(txt.getText().toString());
            if(diem > 10 || diem <0) return false;
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    private void setControl(){
        maLTC=findViewById(R.id.maLTC);
        tenMH=findViewById(R.id.tenMH);
        maSV = findViewById(R.id.maSV);
        tenSV = findViewById(R.id.tenSV);
        diemCC = findViewById(R.id.diemCC);
        diemGK = findViewById(R.id.diemGK);
        diemCK = findViewById(R.id.diemCK);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
    private void setEvent(){
        maLTC.setText(NhapDiem_CT_LTC.maLTC_ct_ltc);
        tenMH.setText(NhapDiem_CT_LTC.tenMH_ct_ltc);
        maSV.setText(NhapDiem_CT_LTC.maSV_ct_ltc);
        getDiemSV();
    }

    private void getDiemSV() {
        try {
            conn = DatabaseManager.getConnection();
            if(conn !=null){
                String query =
                        "SELECT SV.HoTen, DK.DIEMCC,DK.DiemGK,DK.DiemCK\n" +
                                "FROM SINHVIEN SV, DangKi DK\n" +
                                "WHERE DK.MaLTC='"+ NhapDiem_CT_LTC.maLTC_ct_ltc+"' AND DK.MaSV='"+ NhapDiem_CT_LTC.maSV_ct_ltc+"' AND SV.MaSV = DK.MaSV";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    tenSV.setText(rs.getString(1));
                    diemCC.setText(Float.toString(rs.getFloat(2)));
                    diemGK.setText(Float.toString(rs.getFloat(3)));
                    diemCK.setText(Float.toString(rs.getFloat(4)));
                }
                conn.close();
            }
            else{
                connectionResult="Check Connection";
            }
        }
        catch (Exception e){
            Log.e("",e.getMessage());
        }
    }

    public void alertSuccess(String content) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(CapNhatDiem.this);
        bulider.setMessage(content);
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(CapNhatDiem.this, NhapDiem_CT_LTC.class);
                intent.putExtra("maGiangVien", TrangChuGV.maGV);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }

}