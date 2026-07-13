package com.example.study

import android.os.Bundle
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

    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTodo = findViewById<EditText>(R.id.editTodo)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val recyclerTodos = findViewById<RecyclerView>(R.id.recyclerTodos)

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
            val title = editTodo.text.toString().trim()
            if (title.isNotEmpty()) {
                viewModel.addTodo(title) // Ra lệnh cho ViewModel, không tự sửa list ở đây
                editTodo.text.clear()
            }
        }
    }
}