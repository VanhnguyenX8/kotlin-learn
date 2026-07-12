package com.example.study

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class TodoStorage(context: Context) {

    private val prefs = context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)

    fun load(): MutableList<Todo> {
        val raw = prefs.getString(KEY_TODOS, null) ?: return mutableListOf()
        val array = JSONArray(raw)
        val todos = mutableListOf<Todo>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            todos.add(
                Todo(
                    id = obj.getLong("id"),
                    title = obj.getString("title"),
                    done = obj.getBoolean("done")
                )
            )
        }
        return todos
    }

    fun save(todos: List<Todo>) {
        val array = JSONArray()
        for (todo in todos) {
            val obj = JSONObject()
            obj.put("id", todo.id)
            obj.put("title", todo.title)
            obj.put("done", todo.done)
            array.put(obj)
        }
        prefs.edit().putString(KEY_TODOS, array.toString()).apply()
    }

    companion object {
        private const val KEY_TODOS = "todos"
    }
}