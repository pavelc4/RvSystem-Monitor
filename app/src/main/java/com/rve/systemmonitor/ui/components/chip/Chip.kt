package com.rve.systemmonitor.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A custom chip component used to display status or category information.
 *
 * @param text The text to be displayed inside the chip.
 * @param containerColor The background color of the chip.
 * @param textColor The color of the text inside the chip.
 * @param modifier The [Modifier] to be applied to the chip.
 */
@Composable
fun BadgeChip(text: String, containerColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = FontWeight.Bold,
        )
    }
}

/**
 * A compact chip used to display a label and a value, typically in a grid or row.
 *
 * @param label The label text.
 * @param value The value text.
 * @param modifier The [Modifier] to be applied to the chip.
 */
@Composable
fun CompactInfoChip(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .padding(4.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
