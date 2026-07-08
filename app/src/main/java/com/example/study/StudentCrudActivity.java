package com.example.study;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentCrudActivity extends AppCompatActivity implements CrudStudentAdapter.OnStudentActionListener {

    private final List<Student> studentList = new ArrayList<>();
    private CrudStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_crud);

        studentList.add(new Student("Việt Anh", 22));
        studentList.add(new Student("Nguyễn Văn A", 21));
        studentList.add(new Student("Trần Văn B", 20));

        RecyclerView recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CrudStudentAdapter(studentList, this);
        recyclerView.setAdapter(adapter);

        Button btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(v -> showStudentDialog(-1));
    }

    @Override
    public void onEdit(int position) {
        showStudentDialog(position);
    }

    @Override
    public void onDelete(int position) {
        studentList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show();
    }

    private void showStudentDialog(int position) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_student, null);
        EditText edtDialogName = dialogView.findViewById(R.id.edtDialogName);
        EditText edtDialogAge = dialogView.findViewById(R.id.edtDialogAge);

        boolean isEdit = position != -1;
        if (isEdit) {
            Student student = studentList.get(position);
            edtDialogName.setText(student.getName());
            edtDialogAge.setText(String.valueOf(student.getAge()));
        }

        new AlertDialog.Builder(this)
                .setTitle(isEdit ? "Sửa sinh viên" : "Thêm sinh viên")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = edtDialogName.getText().toString().trim();
                    String ageText = edtDialogAge.getText().toString().trim();

                    if (name.isEmpty() || ageText.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int age = Integer.parseInt(ageText);

                    if (isEdit) {
                        Student student = studentList.get(position);
                        student.setName(name);
                        student.setAge(age);
                        adapter.notifyItemChanged(position);
                    } else {
                        studentList.add(new Student(name, age));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }
}