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
fun BasketCard(summaries: List<BasketFormulaSummary>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Paniers & Stocks", style = MaterialTheme.typography.titleMedium)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f))
            summaries.forEach { summary ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(summary.name)
                    Text("${summary.actualCount} / ${summary.expectedCount}")
                }
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
fun BasketCardLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            BasketCard(mockSummaries)
        }
    }
}

@Preview
@Composable
fun BasketCardDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            BasketCard(mockSummaries)
        }
    }
}

private val mockSummaries = listOf(
    BasketFormulaSummary("b1", "Mini", 20, 15),
    BasketFormulaSummary("b2", "Solo", 30, 30)
)