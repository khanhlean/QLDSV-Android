package com.example.adapter;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QLDSV.R;

public class TableViewHolder extends RecyclerView.ViewHolder {
    TableLayout resultTable;
    TextView semesterBar;
    public TableViewHolder(@NonNull View itemView) {
        super(itemView);
        setControl(itemView);
    }
    private void setControl(View itemView){
        resultTable = itemView.findViewById(R.id.result_table);
        semesterBar = itemView.findViewById(R.id.semester_bar);
    }
}
