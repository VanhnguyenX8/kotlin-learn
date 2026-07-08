package com.example.study;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // event onclick
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(username.isEmpty()) {
                edtUsername.setError("Username khong duoc de trong");
                return;
            }

            if(password.isEmpty()) {
                edtPassword.setError("Password khong duoc de trong");
                return;
            }

            Log.d("Login", "Username: " + username);
            Log.d("Login", "Password: " + password);

            Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
        });
    }
}
