package com.cloffygames.techcareertodoapptask.uix.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cloffygames.techcareertodoapptask.data.entity.Task
import com.cloffygames.techcareertodoapptask.ui.theme.CheckedCheckboxColor
import com.cloffygames.techcareertodoapptask.ui.theme.CompletedTaskColor
import com.cloffygames.techcareertodoapptask.ui.theme.CompletedTaskContentColor
import com.cloffygames.techcareertodoapptask.ui.theme.HighPriorityColor
import com.cloffygames.techcareertodoapptask.ui.theme.LowPriorityColor
import com.cloffygames.techcareertodoapptask.ui.theme.MediumPriorityColor
import com.cloffygames.techcareertodoapptask.ui.theme.SuseFontFamily
import com.cloffygames.techcareertodoapptask.ui.theme.UncheckedCheckboxColor
import com.cloffygames.techcareertodoapptask.ui.theme.UncompletedTaskContentColor

/**
 * TaskItem composable fonksiyonu, tek bir görev kartını görüntüler.
 * Görevlerin başlığı, açıklaması, tamamlanma durumu ve öncelik seviyesi burada gösterilir.
 * Ayrıca, görev tamamlanabilir, silinebilir ve detaylarına gidilebilir.
 *
 * @param task Görev nesnesi.
 * @param onTaskClick Göreve tıklandığında detaylarına gitme işlemi.
 * @param onTaskCompletedChange Görevin tamamlanma durumunu değiştirme işlemi.
 * @param onTaskDelete Görevi silme işlemi.
 */
@Composable
fun TaskItem(
    task: Task,
    onTaskClick: () -> Unit,
    onTaskCompletedChange: (Boolean) -> Unit,
    onTaskDelete: () -> Unit
) {
    // Görevin tamamlanma durumuna göre true/false değeri belirlenir
    val isTaskCompleted = task.isCompleted == 1

    // Görevin öncelik seviyesine göre kartın rengi belirlenir
    val priorityColor = when (task.priority) {
        1 -> LowPriorityColor // Düşük öncelik rengi
        2 -> MediumPriorityColor // Orta öncelik rengi
        3 -> HighPriorityColor // Yüksek öncelik rengi
        else -> Color.LightGray // Bilinmeyen öncelik rengi
    }
    // Görevin öncelik seviyesine göre kartın rengi belirlenir
    val iconsPriorityColor = when (task.priority) {
        1 -> HighPriorityColor // Düşük öncelik rengi
        2 -> LowPriorityColor // Orta öncelik rengi
        3 -> MediumPriorityColor // Yüksek öncelik rengi
        else -> Color.LightGray // Bilinmeyen öncelik rengi
    }

    // Kart görünümü, görev bilgilerini içerir
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onTaskClick() }, // Göreve tıklandığında detaylarına gitmek için
        colors = CardDefaults.cardColors(
            containerColor = if (isTaskCompleted) CompletedTaskColor else priorityColor, // Tamamlanmış görevlerde gri renk
            contentColor = if (isTaskCompleted) CompletedTaskContentColor else UncompletedTaskContentColor // Yazı rengi
        ),
        elevation = CardDefaults.cardElevation(8.dp) // Kartın gölgelendirmesi
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // İç boşluk
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Görevin tamamlanma durumu için checkbox
            Checkbox(
                checked = isTaskCompleted,
                onCheckedChange = { onTaskCompletedChange(it) }, // Tamamlanma durumu değiştirildiğinde çağrılır
                colors = CheckboxDefaults.colors(
                    checkedColor = CheckedCheckboxColor, // Tamamlanmış görev için yeşil renk
                    uncheckedColor = iconsPriorityColor // Tamamlanmamış görev için gri renk
                )
            )

            // Görev başlığı ve açıklamasını içeren sütun
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                // Görev başlığı, eğer görev tamamlanmışsa üzeri çizili gösterilir
                Text(
                    text = task.title,
                    style = TextStyle(
                        fontFamily = SuseFontFamily,
                        fontSize = 20.sp
                    ),
                    textDecoration = if (isTaskCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(4.dp)) // Başlık ve açıklama arasında boşluk
                // Görev açıklaması
                Text(
                    text = task.description,
                    style = TextStyle(
                        fontFamily = SuseFontFamily,
                        fontSize = 16.sp
                    )
                )
            }

            // Silme butonu ve öncelik seviyesi göstergesi
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Görev silme butonu
                IconButton(onClick = onTaskDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task", tint = iconsPriorityColor)
                }
                // Görevin öncelik seviyesini gösteren metin
                Text(
                    text = when (task.priority) {
                        1 -> "Low" // Düşük öncelik
                        2 -> "Medium" // Orta öncelik
                        3 -> "High" // Yüksek öncelik
                        else -> "Unknown" // Bilinmeyen öncelik
                    },
                    style = TextStyle(
                        fontFamily = SuseFontFamily,
                        fontSize = 14.sp
                    ),
                    color = iconsPriorityColor
                )
            }
        }
    }
}