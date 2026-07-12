package com.example.study

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var storage: TodoStorage
    private lateinit var todos: MutableList<Todo>
    private lateinit var adapter: TodoAdapter
    private var nextId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storage = TodoStorage(this)
        todos = storage.load()
        nextId = (todos.maxOfOrNull { it.id } ?: -1L) + 1L

        val editTodo = findViewById<EditText>(R.id.editTodo)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val recyclerTodos = findViewById<RecyclerView>(R.id.recyclerTodos)

        adapter = TodoAdapter(
            todos,
            onToggle = { todo ->
                todo.done = !todo.done
                storage.save(todos)
                adapter.notifyDataSetChanged()
            },
            onDelete = { todo ->
                val index = todos.indexOfFirst { it.id == todo.id }
                if (index != -1) {
                    todos.removeAt(index)
                    storage.save(todos)
                    adapter.notifyItemRemoved(index)
                }
            }
        )

        recyclerTodos.layoutManager = LinearLayoutManager(this)
        recyclerTodos.adapter = adapter

        buttonAdd.setOnClickListener {
            val title = editTodo.text.toString().trim()
            if (title.isNotEmpty()) {
                todos.add(Todo(id = nextId++, title = title))
                storage.save(todos)
                adapter.notifyItemInserted(todos.size - 1)
                editTodo.text.clear()
            }
        }
    }
}