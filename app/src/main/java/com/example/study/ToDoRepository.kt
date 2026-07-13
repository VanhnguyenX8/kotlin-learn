package com.example.study

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

// @Singleton: Nghĩa là "Duy nhất". Cả ứng dụng chỉ tạo ra 1 đối tượng Repository này thôi.
// Giúp tiết kiệm bộ nhớ và đảm bảo dữ liệu đồng nhất ở mọi nơi.
@Singleton
class ToDoRepository @Inject constructor(
    // @Inject constructor: Bảo Hilt cách để tạo ra lớp này.
    // @ApplicationContext: Hilt sẽ tự lấy 'context' của App truyền vào cho bạn.
    // private val:
    // - 'private': Chỉ lớp này mới dùng được biến 'context'.
    // - 'val': Biến hằng số, không thể thay đổi sau khi đã gán.
    @ApplicationContext private val context: Context
) {
    // Repository đóng vai trò là "nguồn dữ liệu duy nhất".
    // Nó không quan tâm đến UI, chỉ quan tâm cách Lưu và Lấy dữ liệu.
    private val storage = TodoStorage(context)

    fun getTodos(): MutableList<Todo> = storage.load()

    fun saveTodo(todo: List<Todo>) = storage.save(todo)
}