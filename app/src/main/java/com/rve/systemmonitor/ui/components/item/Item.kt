package com.rve.systemmonitor.ui.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A simple item displaying a label and a corresponding value.
 *
 * @param modifier The [Modifier] to be applied to the item.
 * @param label The label text.
 * @param labelColor The color of the label text.
 * @param value The value text.
 * @param valueColor The color of the value text.
 */
@Composable
fun InfoItem(
    modifier: Modifier = Modifier,
    label: String,
    labelColor: Color = MaterialTheme.colorScheme.onSurface,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = labelColor,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

/**
 * An item used in help or information sections, featuring a title and a detailed description.
 *
 * @param title The title of the help item.
 * @param description The detailed description text.
 * @param modifier The [Modifier] to be applied to the item.
 */
@Composable
fun HelpItem(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
    }
}
