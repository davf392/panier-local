package com.davf392.panierlocal.ui.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.staff_dashboard.AlertPriority
import com.davf392.panierlocal.data.staff_dashboard.DashboardAlert
import com.davf392.panierlocal.ui.AddIcon
import com.davf392.panierlocal.ui.CheckedIcon
import com.davf392.panierlocal.ui.UncheckedIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AlertCard(alerts: List<DashboardAlert>, onResolve: (String) -> Unit, onReport: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Alertes", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onErrorContainer)
                Button(
                    onClick = onReport,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer, contentColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Icon(AddIcon, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Signaler")
                }
            }

            if (alerts.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f))
                alerts.forEach { alert ->
                    var isResolving by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = alert.message,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { 
                            isResolving = true
                            coroutineScope.launch {
                                delay(300.milliseconds)
                                onResolve(alert.id)
                            }
                        }) {
                            Icon(
                                imageVector = if (isResolving) CheckedIcon else UncheckedIcon,
                                contentDescription = "Résoudre",
                                tint = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAlertCard() {
    AlertCard(
        alerts = listOf(
            DashboardAlert("a1", "Test d'alerte", AlertPriority.WARNING)
        ),
        onResolve = {},
        onReport = {}
    )
}
