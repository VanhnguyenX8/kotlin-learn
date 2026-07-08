package com.example.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrudStudentAdapter extends RecyclerView.Adapter<CrudStudentAdapter.StudentViewHolder> {

    public interface OnStudentActionListener {
        void onEdit(int position);
        void onDelete(int position);
    }

    private final List<Student> studentList;
    private final OnStudentActionListener listener;

    public CrudStudentAdapter(List<Student> studentList, OnStudentActionListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_crud, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvName.setText("👤 " + student.getName());
        holder.tvAge.setText(student.getAge() + " tuổi");

        holder.btnEdit.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onEdit(pos);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onDelete(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAge;
        Button btnEdit;
        Button btnDelete;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}