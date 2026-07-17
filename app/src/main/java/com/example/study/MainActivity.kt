package com.example.study

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

// @AndroidEntryPoint: Đánh dấu đây là nơi Hilt sẽ "đổ" các đối tượng vào (như ViewModel).
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val airplaneModeReceiver = AirplaneModeReceiver()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        registerReceiver(airplaneModeReceiver, filter)

        val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)

    }

    /// vi du vao danh ba
    fun connectContact() {
        try {
            var cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, /// Dia chi thu vien danh ba
                null, /// lay tat cac cac cot
                null, /// khong loc hang nao
                null, /// khong co doi so loc
                null, /// khong sap xep
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val name = it.getString(nameIndex)
                    println("Tên bạn bè: $name")
                }
            }
        }

        catch (e: Exception) {
        print(e.printStackTrace())
        }

    }
    fun onTapIntent() {
        val intent = Intent(this, SecondActivity::class.java)
        /// gửi
        intent.putExtra("name", "Viet anh")
               intent.putExtra("age", "22")
        startActivity(intent)
        /// nhận
        val name = intent.getStringExtra("name");
        val age = intent.getStringExtra("age")

    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneModeReceiver)
    }
}