package com.davf392.panierlocal.features.staff_dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.AddIcon
import com.davf392.panierlocal.core.designsystem.CheckedIcon
import com.davf392.panierlocal.core.designsystem.UncheckedIcon
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AlertCard(alerts: List<DashboardAlert>, onResolve: (String) -> Unit, onReport: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Alertes", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(
                    onClick = onReport,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Icon(AddIcon, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Signaler")
                }
            }

            if (alerts.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                alerts.forEach { alert ->
                    var isResolving by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val priorityColor = when (alert.priority) {
                            AlertPriority.INFO -> MaterialTheme.colorScheme.primary
                            AlertPriority.WARNING -> MaterialTheme.colorScheme.tertiary
                            AlertPriority.CRITICAL -> MaterialTheme.colorScheme.error
                        }
                        
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(24.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(priorityColor)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = alert.message,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
fun AlertCardLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            AlertCard(
                alerts = previewAlerts,
                onResolve = {},
                onReport = {}
            )
        }
    }
}

@Preview
@Composable
fun AlertCardDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            AlertCard(
                alerts = previewAlerts,
                onResolve = {},
                onReport = {}
            )
        }
    }
}

private val previewAlerts = listOf(
    DashboardAlert("a1", "Test d'alerte", AlertPriority.WARNING)
)
