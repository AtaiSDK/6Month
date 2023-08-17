package com.example.a6month

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a6month.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskAdapter = TaskAdapter(this, viewModel, object : TaskAdapter.DeleteTaskListener {
            override fun onDeleteTask(task: Task) {
                showDeleteTaskDialog(task)
            }
        })

        val recycler: RecyclerView = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = taskAdapter

        val saveBtn: Button = findViewById(R.id.btnAddTask)
        val edText: EditText = findViewById(R.id.edText)

        saveBtn.setOnClickListener {
            val taskTitle = edText.text.toString()
            if (taskTitle.isNotEmpty()) {
                viewModel.addTask(taskTitle)
                taskAdapter.setTasks(viewModel.getTaskList())
                edText.text.clear()
            }
        }
    }

    private fun showDeleteTaskDialog(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.removeTask(task)
                taskAdapter.setTasks(viewModel.getTaskList())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}