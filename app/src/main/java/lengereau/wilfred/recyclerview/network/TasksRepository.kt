package lengereau.wilfred.recyclerview.network

import lengereau.wilfred.recyclerview.Task

class TasksRepository {
    val tasksService = Api.tasksService

    suspend fun loadTasks(): List<Task>? {
        val tasksResponse = tasksService.getTasks()
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }

    suspend fun createTask(task: Task): Task? {
        val tasksResponse = tasksService.createTask(task)
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }

    suspend fun deleteTask(task: Task): Boolean {
        val tasksResponse = tasksService.deleteTask(task.id)
        return tasksResponse.isSuccessful
    }
}