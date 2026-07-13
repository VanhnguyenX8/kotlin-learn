package com.example.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// @HiltViewModel: Bảo Hilt hãy quản lý vòng đời của lớp này.
// ViewModel sẽ không bị hủy khi bạn xoay màn hình điện thoại.
@HiltViewModel
class TodoViewModel @Inject constructor(
    // Hilt sẽ tự tìm Repository đã khai báo ở bước 2 để đưa vào đây.
    private val repository: ToDoRepository
) : ViewModel() {

    // _todos (MutableLiveData):
    // - 'Mutable': Có thể thay đổi được (thêm/sửa/xóa).
    // - 'private': Chỉ ViewModel mới có quyền dùng hàm .value = ... để đổi dữ liệu.
    private val _todos = MutableLiveData<MutableList<Todo>>()

    // todo (LiveData):
    // - Bản "chỉ đọc" của _todos.
    // - 'get() = _todos': View chỉ có thể "nhìn" chứ không thể "sửa".
    // - Tác dụng: Khi _todos thay đổi, bất kỳ ai đang 'observe' biến 'todo' này sẽ tự động biết.
    val todo: LiveData<MutableList<Todo>> get() = _todos

    init {
        // init: Chạy ngay khi ViewModel vừa được sinh ra.
        // Lấy dữ liệu từ máy lên để hiển thị ngay.
        _todos.value = repository.getTodos()
    }

    // Hàm nội bộ để gom nhóm việc Lưu và Thông báo cập nhật UI
    private fun save(list: MutableList<Todo>) {
        repository.saveTodo(list)
        _todos.value = list // Kích hoạt sự kiện thay đổi dữ liệu
    }

    fun addTodo(title: String) {
        val currentList = _todos.value ?: mutableListOf()
        val nextId = (currentList.maxOfOrNull { it.id } ?: -1L) + 1L
        currentList.add(Todo(id = nextId, title = title))
        save(currentList)
    }

    fun toggleTodo(todo: Todo) {
        todo.done = !todo.done
        save(_todos.value ?: mutableListOf())
    }

    fun deleteTodo(todo: Todo) {
        val currentList = _todos.value ?: mutableListOf()
        currentList.remove(todo)
        save(currentList)
    }
}