package com.example.study

/// dùng data để có equals và hashcode, toString, copy
/// khi cần kế thừa thi không cần dùng data class

data class Todo(
    val id: Long,
    var title: String,
    var done: Boolean = false
)

