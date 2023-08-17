package com.example.a6month

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a6month.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val viewModel: TaskViewModel,
    private val deleteTaskListener: DeleteTaskListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val taskList: MutableList<Task> = mutableListOf()

    fun setTasks(tasks: List<Task>) {
        taskList.clear()
        taskList.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(task: Task) {
            titleTextView.text = task.title
            checkbox.isChecked = task.isCompleted

            itemView.setOnLongClickListener {
                deleteTaskListener.onDeleteTask(task)
                true
            }

            checkbox.setOnClickListener {
                viewModel.toggleTaskCompletion(task)
            }
        }
    }

    interface DeleteTaskListener {
        fun onDeleteTask(task: Task)
    }
}