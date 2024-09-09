package com.cloffygames.techcareertodoapptask.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.uix.uicomponent.PriorityRadioButton
import com.cloffygames.techcareertodoapptask.uix.viewmodel.DetailViewModel

/**
 * DetailScreen, bir görev detayının görüntülendiği ve düzenlendiği ekrandır.
 * Kullanıcı burada görevin başlığını, açıklamasını ve öncelik seviyesini değiştirebilir.
 *
 * @param navController Ekranlar arası gezinme kontrolü sağlar.
 * @param detailViewModel Görev detaylarını yönetmek için kullanılan ViewModel.
 * @param taskObject Düzenlenmek istenen görevi temsil eden Task nesnesi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, detailViewModel: DetailViewModel, taskObject: Task) {

    // Görev başlığı, açıklaması ve önceliği için yerel durumlar oluşturuluyor.
    var taskTitle by remember { mutableStateOf(TextFieldValue(taskObject.title)) }
    var taskDescription by remember { mutableStateOf(TextFieldValue(taskObject.description)) }
    var taskPriority by remember { mutableStateOf(taskObject.priority) }

    Scaffold(
        topBar = {
            // Üst bar (TopAppBar), geri dönme butonu ve başlığı içerir.
            CenterAlignedTopAppBar(
                title = { Text(text = "Task Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFA726), // Üst bar arka plan rengi
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            // Ana içerik alanı, görev detaylarının düzenlenebileceği formu içerir.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF0F0F0))
                    .padding(paddingValues)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Görev başlığı giriş alanı
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text("Task Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

                    // Görev açıklaması giriş alanı
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Task Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        maxLines = 4,
                        shape = MaterialTheme.shapes.medium
                    )

                    // Görev önceliği seçimi
                    Text("Priority", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))

                    // Öncelik seviyesini seçmek için radyo butonları içeren kart bileşeni
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(12.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Düşük öncelik radyo butonu
                                PriorityRadioButton(
                                    selected = taskPriority == 1,
                                    onClick = { taskPriority = 1 },
                                    label = "Low",
                                    color = Color(0xFF90CAF9)
                                )
                                // Orta öncelik radyo butonu
                                PriorityRadioButton(
                                    selected = taskPriority == 2,
                                    onClick = { taskPriority = 2 },
                                    label = "Medium",
                                    color = Color(0xFFFFF176)
                                )
                                // Yüksek öncelik radyo butonu
                                PriorityRadioButton(
                                    selected = taskPriority == 3,
                                    onClick = { taskPriority = 3 },
                                    label = "High",
                                    color = Color(0xFFF44336)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Görevi güncelle butonu
                    Button(
                        onClick = {
                            // Güncellenmiş görev oluşturulup ViewModel'e gönderilir
                            val updatedTask = taskObject.copy(
                                title = taskTitle.text,
                                description = taskDescription.text,
                                priority = taskPriority
                            )
                            detailViewModel.updateTask(updatedTask)
                            navController.popBackStack() // Güncelleme sonrası geri dön
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA726),
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Update Task", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    )
}
