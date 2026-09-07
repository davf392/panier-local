package com.davf392.panierlocal.ui.features.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.staff_dashboard.AlertPriority
import com.davf392.panierlocal.data.staff_dashboard.DashboardAlert
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AlertCard(alerts: List<DashboardAlert>) {
    if (alerts.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Alertes", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onErrorContainer)
            Spacer(modifier = Modifier.height(8.dp))
            alerts.forEach { alert ->
                Text(alert.message, color = MaterialTheme.colorScheme.onErrorContainer)
            }
        }
    }
}

@Preview
@Composable
fun PreviewAlertCard() {
    val alerts = listOf(
        DashboardAlert("a1", "Test d'alerte", AlertPriority.WARNING)
    )
    AlertCard(alerts)
}
