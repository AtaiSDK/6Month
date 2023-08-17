package com.example.a6month

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private val taskList = mutableListOf<Task>()

    fun getTaskList(): List<Task> {
        return taskList
    }

    fun addTask(title: String) {
        taskList.add(Task(title))
    }

    fun toggleTaskCompletion(task: Task) {
        task.isCompleted = !task.isCompleted
    }

    fun removeTask(task: Task) {
        taskList.remove(task)
    }
}