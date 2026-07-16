package com.example.study

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

// @AndroidEntryPoint: Đánh dấu đây là nơi Hilt sẽ "đổ" các đối tượng vào (như ViewModel).
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // 'by viewModels()': Một cách khởi tạo ViewModel thông minh.
    // Nó sẽ tự động kết nối với Hilt để lấy TodoViewModel về mà bạn không cần dùng từ 'new'.
    private val viewModel: TodoViewModel by viewModels()
    private val airplaneModeReceiver = AirplaneModeReceiver()

    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filter)
        val editTodo = findViewById<EditText>(R.id.editTodo)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val recyclerTodos = findViewById<RecyclerView>(R.id.recyclerTodos)
        connectContact()
        // LẮNG NGHE SỰ THAY ĐỔI (Observe):
        // Đây là trái tim của MVVM. Bạn không cần tự tay cập nhật Adapter nữa.
        // Cứ hễ dữ liệu trong ViewModel thay đổi, khối code này sẽ TỰ CHẠY.
        viewModel.todo.observe(this) { list ->
            if (!::adapter.isInitialized) {
                // Khởi tạo adapter lần đầu tiên
                adapter = TodoAdapter(list,
                    onToggle = { viewModel.toggleTodo(it) }, // Gửi yêu cầu về ViewModel xử lý
                    onDelete = { viewModel.deleteTodo(it) }
                )
                recyclerTodos.adapter = adapter
                recyclerTodos.layoutManager = LinearLayoutManager(this)
            } else {
                // Khi có dữ liệu mới, chỉ cần báo cho Adapter vẽ lại
                adapter.notifyDataSetChanged()
            }
        }

        buttonAdd.setOnClickListener {
            onTapIntent()

//            val title = editTodo.text.toString().trim()
//            if (title.isNotEmpty()) {
//                viewModel.addTodo(title) // Ra lệnh cho ViewModel, không tự sửa list ở đây
//                editTodo.text.clear()
//            }
        }
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