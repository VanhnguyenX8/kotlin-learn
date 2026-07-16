package com.example.study

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Vẫn giữ cái này để hỗ trợ Android 12+ mượt mà
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val tvHello = findViewById<TextView>(R.id.tvHello)

        // 2. Tạo hiệu ứng động (ví dụ: phóng to và mờ dần vào)
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 1500 // Chạy trong 1.5 giây

        tvHello.startAnimation(animation)

        // 3. Đợi hiệu ứng chạy xong rồi chuyển màn hình
        tvHello.postDelayed({
            startMainActivity()
        }, 2000) // Đợi 2 giây tổng cộng
    }

    private fun startMainActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}