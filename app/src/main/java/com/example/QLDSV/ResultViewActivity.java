package com.example.QLDSV;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Database.DatabaseManager;
import com.example.Objects.Semester;
import com.example.Objects.SubjectScore;
import com.example.adapter.TableViewAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResultViewActivity extends AppCompatActivity {
    Button btnFilter;
    EditText edtScoreFilter;
    ArrayList<ArrayList<SubjectScore>> listSubjectScore;
    Spinner spnSemester;
    RecyclerView recyclerView;
    ArrayList<SubjectScore> unfilteredSSList;
    String maSinhVien;
    ImageButton btnBack;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_result);
        Intent intent = getIntent();
        maSinhVien = intent.getStringExtra("maSinhVien");

        getData();
        setRenderingViews();
        setControl();
        setEvent();
    }
    private void setRenderingViews(){
        setResultTablesView();
    }
    private void getData(){
        getDBData();
        filterSubjectScore();
    }
    private void setControl(){
        btnFilter = findViewById(R.id.button_filter);
        edtScoreFilter = findViewById(R.id.edit_search_trans);
        spnSemester = findViewById(R.id.semester_spinner);
        btnBack = findViewById(R.id.btn_back);
        refreshLayout = findViewById(R.id.learning_result_container);
    }
    private void setEvent(){
        setSpnSemesterEvent();
        setBtnFilterEvent();
        edtScoreFilter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard(ResultViewActivity.this);
                    filterTableByText(edtScoreFilter.getText().toString());
                    return true;
                }
                return false;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultViewActivity.this, TrangChuSV.class);
                intent.putExtra("maSinhVien", maSinhVien);
                startActivity(intent);
            }
        });

        refreshLayout.setDistanceToTriggerSync(300);
        refreshLayout.setOnRefreshListener(() -> {
            getData();
//            spnSemester.setSelection(0, true);
//            edtScoreFilter.getText().clear();
            recyclerView.setAdapter(new TableViewAdapter(ResultViewActivity.this, listSubjectScore));
            refreshLayout.setRefreshing(false);
        });
    }
    private void setBtnFilterEvent(){
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterTableByText(edtScoreFilter.getText().toString());
                hideKeyboard(ResultViewActivity.this);
            }
        });
    }
    public String toUnaccentedString(String input){
        String unaccentedString = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return unaccentedString.trim().replaceAll("\\s+", " ").toLowerCase();
    }
    private void filterTableByText(String rawSearch){
        String search = toUnaccentedString(rawSearch);
        ArrayList<ArrayList<SubjectScore>> filteredSjScoreList = new ArrayList<>();
        for (int i = 0; i < listSubjectScore.size(); i++) {
            ArrayList<SubjectScore> tmpFilteredScoreList = new ArrayList<>();
            ArrayList<SubjectScore> tmpCheckingScoreList = listSubjectScore.get(i);
            for (int j = 0; j < listSubjectScore.get(i).size(); j++) {
                SubjectScore subjectScore = tmpCheckingScoreList.get(j);
                if(toUnaccentedString(subjectScore.getSubjectName()).contains(search)){
                    tmpFilteredScoreList.add(subjectScore);
                }
            }
            if(tmpFilteredScoreList.size() != 0)
                filteredSjScoreList.add(tmpFilteredScoreList);
        }
        if(filteredSjScoreList.size() == 0) {
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
            return;
        }
        recyclerView.setAdapter(new TableViewAdapter(ResultViewActivity.this, filteredSjScoreList));
    }
    private void setSpnSemesterEvent(){
        //set spinner content
        List<String> listSemester = new ArrayList<>();
        listSemester.add("-- Tất cả học kỳ --");
        for (int i = 0; i < listSubjectScore.size(); i++) {
            Semester semester = (Semester) listSubjectScore.get(i).get(0).getSemester();
            String strSemester = String.format("Học kỳ %d - Năm học %d-%d",semester.getTerm(), semester.getStSchoolYear(), semester.getNdSchoolYear());
            Log.d("semester",strSemester);
            listSemester.add(strSemester);
        }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSemester);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnSemester.setAdapter(adapter);

        //set spinner event
        spnSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<ArrayList<SubjectScore>> temp = new ArrayList<>();

                if(position == 0)
                    temp = listSubjectScore;
                else
                    temp.add(listSubjectScore.get(position-1));

                recyclerView.setAdapter(new TableViewAdapter(ResultViewActivity.this, temp));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getDBData(){
        Connection conn = DatabaseManager.getConnection();
        unfilteredSSList = new ArrayList<>();

        String subjectId, subjectName;
        float attendRatio, midExamRatio, fnlExamRatio, attendScore, midExamScore, fnlExamScore;
        int termNum, fstSchoolYear, sndSchoolYear, creditHours;
        try {
            if(conn != null){
                maSinhVien = "SV1";
                PreparedStatement ps = conn.prepareStatement("Select * from GETSTUDENTINFO('"+maSinhVien+"')");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){

                    subjectId = rs.getString(1);
                    subjectName = rs.getString(2);
                    creditHours = rs.getInt(3);
                    attendRatio = (float) rs.getInt(4)/100;
                    midExamRatio = (float) rs.getInt(5)/100;
                    fnlExamRatio = (float) rs.getInt(6)/100;
                    attendScore = rs.getFloat(7);
                    midExamScore = rs.getFloat(8);
                    fnlExamScore = rs.getFloat(9);
                    termNum = Integer.parseInt(rs.getString(10).substring(2));
                    fstSchoolYear = Integer.parseInt(rs.getString(11).split("-")[0]);
                    sndSchoolYear = Integer.parseInt(rs.getString(11).split("-")[1]);

                    SubjectScore tempSS = new SubjectScore(subjectId, subjectName, "", creditHours, attendRatio, attendScore, midExamRatio, midExamScore, 0, 0, 0, 0, fnlExamRatio, fnlExamScore, 0, 0, 0, new Semester(termNum, fstSchoolYear, sndSchoolYear));
                    tempSS.setCalcFinalScore();
                    unfilteredSSList.add(tempSS);
                }
                ps.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(conn != null)
                    conn.close();
                else
                    Toast.makeText(this, "This is error message!!!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void filterSubjectScore(){
        listSubjectScore = new ArrayList<>();

        for (SubjectScore ssListItem : unfilteredSSList) {
            boolean isAdded = false;
            for (int i = 0; i < listSubjectScore.size(); i++) {
                if( ssListItem.equals( listSubjectScore.get(i).get(0) ) ){
                    listSubjectScore.get(i).add(ssListItem);
                    isAdded = true;
                }
            }
            if(!isAdded){
                listSubjectScore.add(new ArrayList<>());
                listSubjectScore.get(listSubjectScore.size()-1).add(ssListItem);
            }
        }
        listSubjectScore.sort(new Comparator<ArrayList<SubjectScore>>() {
            @Override
            public int compare(ArrayList<SubjectScore> list1, ArrayList<SubjectScore> list2) {
                return list1.get(0).compare(list2.get(0));
            }
        });
    }
    private void setResultTablesView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TableViewAdapter(ResultViewActivity.this, listSubjectScore));
    }
    private void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}