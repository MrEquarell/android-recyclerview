package lengereau.wilfred.recyclerview.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lengereau.wilfred.recyclerview.Task

class TasksViewModel: ViewModel() {
    val taskListLiveData = MutableLiveData<List<Task>?>()
    private val repository = TasksRepository()

    fun loadTasks() {
        viewModelScope.launch {
            val taskList = repository.loadTasks()
            taskListLiveData.postValue(taskList)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            val newTask = repository.createTask(task)
            if (newTask != null) {
                val newList = taskListLiveData.value.orEmpty().toMutableList()
                newList.add(newTask)
                taskListLiveData.postValue(newList)
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            val success = repository.deleteTask(task)
            if (success) {
                val newList = taskListLiveData.value.orEmpty().toMutableList()
                newList.remove(task)
                taskListLiveData.postValue(newList)
            }
        }
    }
}