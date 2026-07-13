package com.example.study

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// @HiltAndroidApp: Đây là chú thích bắt buộc.
// Nó bảo Hilt rằng: "Đây là điểm bắt đầu của App, hãy chuẩn bị sẵn sàng để quản lý các đối tượng."
// Nếu thiếu dòng này, Hilt sẽ không hoạt động được.
@HiltAndroidApp
class StudiApplication : Application() {
    // Thường để trống, Hilt sẽ tự động sinh code bổ trợ ở đây lúc biên dịch.
}