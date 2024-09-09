package com.cloffygames.techcareertodoapptask.uix.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * PriorityRadioButton composable fonksiyonu, kullanıcıların görev öncelik seviyesini seçmesini sağlar.
 * Radyo butonu ve bir etiket içerir. Seçili olup olmamasına göre farklı renkler kullanılır.
 *
 * @param selected Radyo butonunun seçili olup olmadığını belirtir.
 * @param onClick Radyo butonuna tıklanıldığında yapılacak işlemi tanımlar.
 * @param label Radyo butonunun yanında gösterilecek metni belirtir.
 * @param color Radyo butonu ve metin için kullanılacak renk.
 */
@Composable
fun PriorityRadioButton(selected: Boolean, onClick: () -> Unit, label: String, color: Color) {
    // Radyo butonu ve metni yan yana gösteren satır düzeni (Row)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick) // Satırın tamamına tıklanabilirlik sağlar
    ) {
        // Radyo butonu, seçili olup olmamasına göre renklendirilir
        RadioButton(
            selected = selected,
            onClick = onClick, // Tıklandığında ilgili işlem yapılır
            colors = RadioButtonDefaults.colors(
                selectedColor = color, // Seçili olduğunda verilen renk kullanılır
                unselectedColor = Color.Gray // Seçili olmadığında gri renk kullanılır
            )
        )
        // Radyo butonunun yanındaki metin
        Text(
            text = label,
            modifier = Modifier.padding(start = 4.dp), // Buton ile metin arasında boşluk bırakır
            color = if (selected) color else Color.Gray // Seçiliyse belirli renkte, değilse gri renkte gösterilir
        )
    }
}