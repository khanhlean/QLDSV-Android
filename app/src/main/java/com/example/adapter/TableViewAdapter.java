package com.example.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Objects.Semester;
import com.example.Objects.SubjectScore;
import com.example.QLDSV.R;

import java.util.ArrayList;

public class TableViewAdapter extends RecyclerView.Adapter<TableViewHolder> {

    Context context;
    ArrayList<ArrayList<SubjectScore>> listSubjectScore;
    RelativeLayout popupView;
    Dialog myDialog;

    public TableViewAdapter(Context context, ArrayList<ArrayList<SubjectScore>> listSubjectScore) {
        this.context = context;
        this.listSubjectScore = listSubjectScore;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableViewHolder(LayoutInflater.from(context).inflate(R.layout.result_table_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        ArrayList<SubjectScore> listRowContent = listSubjectScore.get(position);
        int rowCount = listRowContent.size();
        int fixedRowCount = holder.resultTable.getChildCount();

        //set content for semester bar
        Semester curSemester = listRowContent.get(0).getSemester();
        @SuppressLint("DefaultLocale") String semesterContent = String.format("Học kỳ %d - Năm học %d-%d",curSemester.getTerm(), curSemester.getStSchoolYear(), curSemester.getNdSchoolYear());
        holder.semesterBar.setText(semesterContent);

        //set content table
        for (int i = 0; i < rowCount; i++) {
            TableRow rowTable = (TableRow) holder.resultTable.getChildAt(i+1);
            SubjectScore rowContent = listRowContent.get(i);
            ((TextView) rowTable.getChildAt(0)).setText(String.valueOf(i+1));
            ((TextView) rowTable.getChildAt(1)).setText(rowContent.getSubjectName());
            ((TextView) rowTable.getChildAt(2)).setText(String.valueOf(rowContent.getCreditHours()));
            ((TextView) rowTable.getChildAt(3)).setText(String.valueOf(rowContent.getFinalScore()));
            ((TextView) rowTable.getChildAt(4)).setText(rowContent.getFinalScoreByChar());
            ((TextView) rowTable.getChildAt(5)).setText("Xem");
        }
        //remove excessive table row
        if(fixedRowCount-2>=rowCount){
            for (int i = 0; i < fixedRowCount-rowCount-1; i++) {
                holder.resultTable.removeView(holder.resultTable.getChildAt(rowCount+1));
            }
        }
        setDialogConentView();
        setDialogTextEvent(holder, position, listRowContent);
    }

    @Override
    public int getItemCount() {
        return listSubjectScore.size();
    }
    private void setDialogTextEvent(TableViewHolder holder, int position, ArrayList<SubjectScore> listRowContent){
        TableLayout table = holder.resultTable;
        for (int i = 1; i < table.getChildCount(); i++) {
            TextView textShowingDialog = (TextView) ((TableRow) table.getChildAt(i)).getChildAt(5);
            int tempIndex = i;
            textShowingDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupView = setDialogContent(popupView, listRowContent.get(tempIndex-1));
                    ImageButton closeButton = popupView.findViewById(R.id.close_button);
                    closeButton.setOnClickListener(view1 -> {
                        myDialog.dismiss();
                    });
                    myDialog.show();
                }
            });
        }
        String s = "";
    }

    private void setDialogConentView(){
        myDialog = new Dialog(context);
        popupView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.popup_detail_result_2, null);
        myDialog.setContentView(popupView);
        popupView = changeSizeLayout(popupView, 370, 435);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private RelativeLayout changeSizeLayout(RelativeLayout layout, int width, int height){
        ViewGroup.LayoutParams layoutRelative =  layout.getLayoutParams();
        layoutRelative.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        layoutRelative.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        layout.setLayoutParams(layoutRelative);
        return layout;
    }
    private RelativeLayout setDialogContent(RelativeLayout popupView, SubjectScore subjectScore){
        TextView subjectId = (TextView) popupView.findViewById(R.id.subject_id);
        TextView creditnNum = (TextView) popupView.findViewById(R.id.credit_num);
        TextView subjectName = (TextView) popupView.findViewById(R.id.subject_name);
        TextView attendRatio = (TextView) popupView.findViewById(R.id.attend_ratio);
        TextView attendScore = (TextView) popupView.findViewById(R.id.attend_score);
        TextView midExamRatio = (TextView) popupView.findViewById(R.id.middle_exam_ratio);
        TextView midExamScore = (TextView) popupView.findViewById(R.id.middle_exam_score);
        TextView practiceRatio = (TextView) popupView.findViewById(R.id.pratice_ratio);
        TextView practiceScore = (TextView) popupView.findViewById(R.id.practice_score);
        TextView seRatio = (TextView) popupView.findViewById(R.id.se_ratio);
        TextView seScore = (TextView) popupView.findViewById(R.id.se_score);
        TextView fnlExamRatio = (TextView) popupView.findViewById(R.id.final_exam_ratio);
        TextView fstFnlExamScore = (TextView) popupView.findViewById(R.id.first_fnlexam_score);
        TextView sndFnlExamScore = (TextView) popupView.findViewById(R.id.second_fnlexam_score);
        TextView thrdFnlExamScore = (TextView) popupView.findViewById(R.id.third_fnalexam_score);
        TextView totalScore = (TextView) popupView.findViewById(R.id.total_score_digit);
        TextView totalScoreByChar = (TextView) popupView.findViewById(R.id.total_score_char);

        //Convert decimals to suitable forms
        int attendRatioInt = (int) (subjectScore.getAttendRatio() * 100) ;
        int midExamRatioInt = (int) (subjectScore.getMiddleExamRatio() * 100) ;
        int practiceRatioInt = (int) (subjectScore.getPracticeRatio() * 100) ;
        int seRatioInt = (int) (subjectScore.getSeRatio() * 100) ;
        int finalExamRatioInt = (int) (subjectScore.getFnlExamRatio() * 100) ;
        int attendScoreInt = (int) subjectScore.getAttendScore();
//        String sndFnlExamScoreInt = isNumberEmpty(subjectScore.getSndFnlExamScore());
//        String thrdFnlExamScoreInt = isNumberEmpty(subjectScore.getThrdFnlExamScore());


        subjectId.setText(subjectScore.getSubjectId());
        creditnNum.setText(strOf(subjectScore.getCreditHours()));
        subjectName.setText(subjectScore.getSubjectName());
        attendRatio.setText(strOf(attendRatioInt));
        attendScore.setText(strOf(attendScoreInt));
        midExamRatio.setText(strOf(midExamRatioInt));
        midExamScore.setText(strOf(subjectScore.getMiddleExamScore()));
        fnlExamRatio.setText(strOf(finalExamRatioInt));
        fstFnlExamScore.setText(strOf(subjectScore.getFstFnlExamScore()));
        totalScore.setText(strOf(subjectScore.getFinalScore()));
        totalScoreByChar.setText(strOf(subjectScore.getFinalScoreByChar()));

        sndFnlExamScore.setText("");
        thrdFnlExamScore.setText("");
        practiceRatio.setText("");
        practiceScore.setText("");
        seRatio.setText("");
        seScore.setText("");

        return popupView;
    }
    private String strOf(Object obj){
        return String.valueOf(obj);
    }
    private String isNumberEmpty(float f){
        if(f == (float)0)
            return "";
        return strOf(f);
    }
}
