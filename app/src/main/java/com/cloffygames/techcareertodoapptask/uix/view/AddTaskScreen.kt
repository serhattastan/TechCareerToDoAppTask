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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.uix.uicomponent.PriorityRadioButton
import com.cloffygames.techcareertodoapptask.uix.viewmodel.AddTaskViewModel
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

/**
 * AddTaskScreen, kullanıcıların yeni bir görev ekleyebileceği ekrandır.
 * Kullanıcı burada görev başlığını, açıklamasını ve önceliğini belirleyebilir.
 *
 * @param navController Ekranlar arası gezinme kontrolünü sağlar.
 * @param addTaskViewModel Yeni görev eklemek için kullanılan ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, addTaskViewModel: AddTaskViewModel) {

    // Görev başlığı, açıklaması ve önceliği için yerel durumlar oluşturuluyor.
    var taskTitle by remember { mutableStateOf(TextFieldValue("")) }
    var taskDescription by remember { mutableStateOf(TextFieldValue("")) }
    var taskPriority by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            // Üst bar (TopAppBar), geri dönme butonu ve başlığı içerir.
            CenterAlignedTopAppBar(
                title = { Text(text = "Add New Task", color = Color.White, style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 20.sp)) },
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
            // Ana içerik alanı, yeni görevin başlığının, açıklamasının ve önceliğinin girilebileceği formu içerir.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF0F0F0)) // Arka plan rengi
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
                        label = { Text("Task Title", style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // TextField'lar arasında boşluk
                        shape = MaterialTheme.shapes.medium // Yuvarlatılmış köşeler
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Görev açıklaması giriş alanı
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Task Description", style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        maxLines = 4,
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Görev önceliği seçimi
                    Text("Priority", style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 16.sp), modifier = Modifier.padding(8.dp))

                    // Öncelik seviyesini seçmek için radyo butonları içeren kart bileşeni
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(12.dp), // Daha fazla gölge efekti
                        shape = MaterialTheme.shapes.large // Yuvarlatılmış köşeler
                    ) {
                        Column(
                            modifier = Modifier
                                .background(color = Color.White) // Beyaz zemin
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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Görevi ekle butonu
                    Button(
                        onClick = {
                            // Yeni görev oluşturulup ViewModel'e gönderilir
                            val newTask = Task(
                                title = taskTitle.text,
                                description = taskDescription.text,
                                priority = taskPriority,
                                isCompleted = 0, // Varsayılan olarak tamamlanmamış görev
                                id = 0 // ID veritabanı tarafından otomatik atanır
                            )
                            addTaskViewModel.insertTask(newTask)
                            navController.popBackStack() // Görev eklendikten sonra geri dön
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA726), // Buton rengi
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Add Task", style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 16.sp))
                    }
                }
            }
        }
    )
}
