package lengereau.wilfred.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import lengereau.wilfred.recyclerview.network.Api
import lengereau.wilfred.recyclerview.network.TasksViewModel


class TasksFragment : Fragment() {
    private val coroutineScope = MainScope()
    private val adapter = TasksAdapter()
    private val viewModel by lazy {
        ViewModelProvider(this).get(TasksViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)
        floatingActionButton.setOnClickListener{
            // Instanciation d'un objet task avec des données préremplies:
            val task = Task(title = "Task!")
            viewModel.addTask(task)
        }
        adapter.onDeleteClickListener = {task ->
            viewModel.deleteTask(task)
        }

        viewModel.taskListLiveData.observe(this, Observer { newList ->
            adapter.tasks = newList.orEmpty()
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            view_info.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        viewModel.loadTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}