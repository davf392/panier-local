package com.davf392.panierlocal.features.staff_dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
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

// --- Previews ---

@Preview
@Composable
fun PermanenceCardLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            PermanenceCard(slots = mockSlots)
        }
    }
}

@Preview
@Composable
fun PermanenceCardDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            PermanenceCard(slots = mockSlots)
        }
    }
}

private val mockSlots = listOf(
    PermanenceSlot("p1", "Jean", "Responsable", true),
    PermanenceSlot("p2", "Marie", "Bénévole", false)
)
