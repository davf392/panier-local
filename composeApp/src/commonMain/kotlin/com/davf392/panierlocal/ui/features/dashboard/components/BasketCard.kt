package com.davf392.panierlocal.ui.features.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.staff_dashboard.BasketFormulaSummary
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

@Preview
@Composable
fun PreviewBasketCard() {
    val summaries = listOf(
        BasketFormulaSummary("b1", "Mini", 20, 15),
        BasketFormulaSummary("b2", "Solo", 30, 30)
    )
    BasketCard(summaries)
}
