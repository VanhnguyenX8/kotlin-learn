package com.example.study;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Việt Anh", 22));
        studentList.add(new Student("Nguyễn Văn A", 21));
        studentList.add(new Student("Trần Văn B", 20));

        StudentAdapter adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);
    }
}