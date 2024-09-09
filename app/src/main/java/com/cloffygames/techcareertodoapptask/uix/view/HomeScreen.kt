package com.cloffygames.techcareertodoapptask.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.uix.uicomponent.TaskItem
import com.cloffygames.techcareertodoapptask.uix.viewmodel.HomeViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val taskList by homeViewModel.taskList.observeAsState(listOf())

    LaunchedEffect(key1 = true) {
        homeViewModel.loadTasks()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Tasks", style = MaterialTheme.typography.headlineMedium, color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFA726),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addTaskScreen") },
                containerColor = Color(0xFFFFA726)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(paddingValues)
            ) {
                TaskList(
                    taskList = taskList,
                    onTaskClick = { task ->
                        val taskJson = Gson().toJson(task)
                        navController.navigate("detailScreen/$taskJson")
                    },
                    onTaskCompletedChange = { task, isCompleted ->
                        homeViewModel.updateTaskCompletion(task.id, if (isCompleted) 1 else 0)
                    },
                    onTaskDelete = { task -> homeViewModel.deleteTask(task) }
                )
            }
        }
    )
}

@Composable
fun TaskList(
    taskList: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskCompletedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (Task) -> Unit
) {
    if (taskList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tasks available.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = { onTaskClick(task) },
                    onTaskCompletedChange = { onTaskCompletedChange(task, it) },
                    onTaskDelete = { onTaskDelete(task) }
                )
            }
        }
    }
}
