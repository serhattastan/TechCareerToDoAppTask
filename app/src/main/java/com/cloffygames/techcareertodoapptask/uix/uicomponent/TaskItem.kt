package com.cloffygames.techcareertodoapptask.uix.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cloffygames.techcareertodoapptask.data.entity.Task

@Composable
fun TaskItem(
    task: Task,
    onTaskClick: () -> Unit,
    onTaskCompletedChange: (Boolean) -> Unit,
    onTaskDelete: () -> Unit
) {
    val isTaskCompleted = task.isCompleted == 1
    val priorityColor = when (task.priority) {
        1 -> Color(0xFF90CAF9)
        2 -> Color(0xFFFFF176)
        3 -> Color(0xFFF44336)
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onTaskClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isTaskCompleted) Color(0xFFE0E0E0) else priorityColor,
            contentColor = if (isTaskCompleted) Color(0xFF757575) else Color(0xFF212121)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = isTaskCompleted,
                onCheckedChange = { onTaskCompletedChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    uncheckedColor = Color(0xFFBDBDBD)
                )
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (isTaskCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onTaskDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task", tint = Color.DarkGray)
                }
                Text(
                    text = when (task.priority) {
                        1 -> "Low"
                        2 -> "Medium"
                        3 -> "High"
                        else -> "Unknown"
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
