package com.example.study.scene.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel (private  val dao: SampleTaskDao) : ViewModel() {
    private val _tasks = MutableStateFlow<List<SampleTask>>(emptyList())
    val tasks: StateFlow<List<SampleTask>> = _tasks.asStateFlow()

    init {
    loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = dao.getAllTask()
        }
    }

    fun addTask(title: String) {
        if(title.isBlank()) return
        viewModelScope.launch {
            dao.insertTask(SampleTask(title = title, isDone = false))
            loadTasks()
        }
    }

    fun toggleDone(task: SampleTask) {
        viewModelScope.launch {
            dao.insertTask(task.copy(isDone = !task.isDone))
            loadTasks()
        }
    }

    fun deleteTask(task: SampleTask) {
        viewModelScope.launch {
            dao.deleteTask(task)
            loadTasks()
        }
    }
}