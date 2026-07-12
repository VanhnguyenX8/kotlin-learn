package com.example.study

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/// Dùng Adapter để nối vào các view list
class TodoAdapter(
    private val todos: MutableList<Todo>,
    private val onToggle: (Todo) -> Unit,
    private val onDelete: (Todo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    /// đây là nested class để đảm bảo tính đóng gói
    class TodoViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkboxDone)
        val title: TextView = itemView.findViewById(R.id.textTitle)
        val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.title.text = todo.title
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = todo.done
        holder.title.paintFlags = if (todo.done) {
            holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        holder.checkBox.setOnCheckedChangeListener { _, _ -> onToggle(todo) }
        holder.deleteButton.setOnClickListener { onDelete(todo) }
    }

    override fun getItemCount(): Int = todos.size
}