package com.example.study;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoLogin = findViewById(R.id.btnGoLogin);
        Button btnGoStudentList = findViewById(R.id.btnGoStudentList);
        Button btnGoStudentCrud = findViewById(R.id.btnGoStudentCrud);

        btnGoLogin.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        btnGoStudentList.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, StudentListActivity.class)));

        btnGoStudentCrud.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, StudentCrudActivity.class)));
    }
}