package com.cloffygames.techcareertodoapptask.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.uix.uicomponent.TaskItem
import com.cloffygames.techcareertodoapptask.uix.viewmodel.HomeViewModel
import com.google.gson.Gson

/**
 * HomeScreen composable fonksiyonu, uygulamanın ana ekranını oluşturur.
 * Kullanıcı görevleri listeleyebilir, görev arayabilir, görev ekleyebilir ve mevcut görevleri düzenleyebilir.
 *
 * @param navController Ekranlar arasında gezinmeyi sağlar.
 * @param homeViewModel Görevleri yöneten ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    // Görev listesini LiveData olarak gözlemler
    val taskList by homeViewModel.taskList.observeAsState(listOf())

    // Arama durumunu ve arama metnini takip eden state'ler
    val searchStatus = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }

    // Ekran yüklendiğinde görevleri yükler
    LaunchedEffect(key1 = true) {
        homeViewModel.loadTasks()
    }

    Scaffold(
        topBar = {
            // Üst bar (TopAppBar), başlık ve arama işlevini içerir
            TopAppBar(
                title = {
                    if (searchStatus.value) {
                        // Arama durumu açık ise arama çubuğu gösterilir
                        TextField(
                            value = tf.value,
                            onValueChange = {
                                tf.value = it
                                homeViewModel.searchTasks(it) // Arama sonuçlarını getirir
                            },
                            label = { Text(text = "Ara") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White,
                            )
                        )
                    } else {
                        // Arama durumu kapalıysa başlık gösterilir
                        Text("My Tasks", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFA726), // Üst bar arka plan rengi
                    titleContentColor = Color.White
                ),
                actions = {
                    // Arama butonu ve arama durumu arasında geçiş
                    IconButton(onClick = {
                        searchStatus.value = !searchStatus.value
                        if (!searchStatus.value) {
                            tf.value = "" // Arama iptal edilince metin temizlenir
                        }
                    }) {
                        Icon(
                            imageVector = if (searchStatus.value) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (searchStatus.value) "Close" else "Search",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            // Görev eklemek için FloatingActionButton
            FloatingActionButton(
                onClick = { navController.navigate("addTaskScreen") },
                containerColor = Color(0xFFFFA726)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        },
        content = { paddingValues ->
            // Görev listesini ve görevlerle ilgili işlemleri içerir
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)) // Arka plan rengi
                    .padding(paddingValues)
            ) {
                TaskList(
                    taskList = taskList,
                    onTaskClick = { task ->
                        val taskJson = Gson().toJson(task)
                        navController.navigate("detailScreen/$taskJson") // Görev detaylarına git
                    },
                    onTaskCompletedChange = { task, isCompleted ->
                        homeViewModel.updateTaskCompletion(task.id, if (isCompleted) 1 else 0) // Görevin tamamlanma durumu güncellenir
                    },
                    onTaskDelete = { task -> homeViewModel.deleteTask(task) } // Görev silinir
                )
            }
        }
    )
}

/**
 * TaskList composable fonksiyonu, görev listesini görüntüler.
 * Görevler üzerinde tamamlanma durumu değiştirme, silme ve detay görüntüleme işlemleri yapılabilir.
 *
 * @param taskList Görevlerin listesi.
 * @param onTaskClick Görev detaylarına gitme işlemi.
 * @param onTaskCompletedChange Görevin tamamlanma durumunu değiştirme işlemi.
 * @param onTaskDelete Görevi silme işlemi.
 */
@Composable
fun TaskList(
    taskList: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskCompletedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (Task) -> Unit
) {
    if (taskList.isEmpty()) {
        // Görev listesi boşsa bilgilendirme mesajı gösterilir
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tasks available.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        // Görevler LazyColumn ile listelenir
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList) { task ->
                // Her görev için TaskItem composable'ı çağrılır
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