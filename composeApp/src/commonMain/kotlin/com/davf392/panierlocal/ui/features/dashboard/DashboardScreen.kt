package com.davf392.panierlocal.ui.features.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davf392.panierlocal.data.staff_dashboard.AlertPriority
import com.davf392.panierlocal.data.staff_dashboard.BasketFormulaSummary
import com.davf392.panierlocal.data.staff_dashboard.DashboardAlert
import com.davf392.panierlocal.data.staff_dashboard.Distribution
import com.davf392.panierlocal.data.staff_dashboard.DistributionStatus
import com.davf392.panierlocal.data.staff_dashboard.PermanenceSlot
import com.davf392.panierlocal.ui.features.dashboard.components.AlertCard
import com.davf392.panierlocal.ui.features.dashboard.components.BasketCard
import com.davf392.panierlocal.ui.features.dashboard.components.PermanenceCard
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.ui.utils.formatDateTime
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardEvent
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardViewModel
import com.davf392.panierlocal.viewmodel.staff_dashboard.DistributionUiState
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(
    selectedDistribution: DistributionUiState,
    onEvent: (DashboardEvent) -> Unit
) {
    var showReportDialog by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                val (date, time) = formatDateTime(
                    selectedDistribution.startTime,
                    selectedDistribution.endTime
                )
                Column {
                    Text(date, style = MaterialTheme.typography.titleMedium)
                    Text(
                        time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                AlertCard(
                    alerts = selectedDistribution.alerts,
                    onResolve = { 
                        onEvent(DashboardEvent.ResolveAlert(it))
                        coroutineScope.launch { snackbarHostState.showSnackbar("Alerte résolue") }
                    },
                    onReport = { showReportDialog = true }
                )
            }
            item { PermanenceCard(selectedDistribution.permanenceSlots) }
            item { BasketCard(selectedDistribution.basketSummaries) }
        }

    }

    if (showReportDialog) {
        var message by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = { Text("Signaler une alerte") },
            text = {
                OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text("Description") })
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(DashboardEvent.ReportAlert(message, AlertPriority.WARNING))
                    showReportDialog = false
                }) { Text("Signaler") }
            }
        )
    }
}

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val selectedDistribution by viewModel.uiState.collectAsState()

    if (selectedDistribution == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        DashboardScreenContent(
            selectedDistribution = selectedDistribution!!,
            onEvent = viewModel::onEvent
        )
    }
}

@Preview
@Composable
fun PreviewDashboardScreen() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val timeZone = TimeZone.currentSystemDefault()
    fun addHours(dt: LocalDateTime, hours: Int): LocalDateTime =
        dt.toInstant(timeZone).plus(hours, DateTimeUnit.HOUR, timeZone).toLocalDateTime(timeZone)

    val mockData = listOf(
        Distribution(
            "dist-001", now, addHours(now, 1),
            "Le Croiseur (Lyon 7)", DistributionStatus.PREPARATION,
            listOf(PermanenceSlot("p1", "Jean", "Responsable", true)),
            listOf(
                BasketFormulaSummary("b1", "Mini", 20, 15),
                BasketFormulaSummary("b2", "Solo", 30, 25),
                BasketFormulaSummary("b3", "Tandem", 15, 10),
                BasketFormulaSummary("b4", "Famille", 10, 8)
            ),
            listOf(DashboardAlert("a1", "Alerte Lyon 7", AlertPriority.INFO))
        ),
        Distribution(
            "dist-002", now, addHours(now, 2),
            "Cabanes (Lyon 8)", DistributionStatus.PREPARATION,
            listOf(PermanenceSlot("p2", "Marie", "Responsable", true)),
            listOf(
                BasketFormulaSummary("b1", "Mini", 25, 25),
                BasketFormulaSummary("b2", "Solo", 35, 30),
                BasketFormulaSummary("b3", "Tandem", 20, 18),
                BasketFormulaSummary("b4", "Famille", 12, 12)
            ),
            emptyList()
        )
    )

    // Mapping for the preview
    fun Distribution.toUiState(): DistributionUiState {
        return DistributionUiState(id, location, startTime, endTime, permanenceSlots, basketSummaries, alerts)
    }

    PanierLocalTheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DashboardScreenContent(
                selectedDistribution = mockData.first().toUiState(),
                onEvent = {}
            )
        }
    }
}
