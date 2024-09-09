package com.cloffygames.techcareertodoapptask.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.ui.theme.AppBarColor
import com.cloffygames.techcareertodoapptask.ui.theme.BackgroundColor
import com.cloffygames.techcareertodoapptask.ui.theme.HighPriorityColor
import com.cloffygames.techcareertodoapptask.ui.theme.LowPriorityColor
import com.cloffygames.techcareertodoapptask.ui.theme.MediumPriorityColor
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
                title = { Text(text = "Task Details", color = Color.White, style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 20.sp)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppBarColor, // Üst bar arka plan rengi
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            // Ana içerik alanı, görev detaylarının düzenlenebileceği formu içerir.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)
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
                            .padding(8.dp),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

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

                    // Görev önceliği seçimi
                    Text("Priority", style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 16.sp), modifier = Modifier.padding(8.dp))

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
                                    color = LowPriorityColor
                                )
                                // Orta öncelik radyo butonu
                                PriorityRadioButton(
                                    selected = taskPriority == 2,
                                    onClick = { taskPriority = 2 },
                                    label = "Medium",
                                    color = MediumPriorityColor
                                )
                                // Yüksek öncelik radyo butonu
                                PriorityRadioButton(
                                    selected = taskPriority == 3,
                                    onClick = { taskPriority = 3 },
                                    label = "High",
                                    color = HighPriorityColor
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
                            containerColor = AppBarColor,
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Update Task", style = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 16.sp))
                    }
                }
            }
        }
    )
}
