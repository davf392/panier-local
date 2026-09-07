package com.davf392.panierlocal.ui.features.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.staff_dashboard.PermanenceSlot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PermanenceCard(slots: List<PermanenceSlot>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Permanence", style = MaterialTheme.typography.titleMedium)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f))
            slots.forEach { slot ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(slot.name)
                    Text(slot.role, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPermanenceCard() {
    val slots = listOf(
        PermanenceSlot("p1", "Jean", "Responsable", true),
        PermanenceSlot("p2", "Marie", "Bénévole", false)
    )
    PermanenceCard(slots)
}
