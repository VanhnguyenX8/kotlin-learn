package com.example.study

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.study.databinding.ActivityUiBinding

class LoginActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityUiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /// chuyen doi file giao dien xml thanh doi tuong koltin
        binding = ActivityUiBinding.inflate(layoutInflater)
        /// hien thi giao dinen duoc khoi tao len man hinh dien thoai
        setContentView(binding.root)
        val initText = intent.getStringExtra("username")
        binding.edtUsername.setText(initText)
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            Log.d("Login", username)
            Log.d("Login", password)
        }
        binding.tvForgot.setOnClickListener {

            Toast.makeText(
                this,
                "Forgot Password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}