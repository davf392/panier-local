package com.davf392.panierlocal.ui.features.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davf392.panierlocal.data.staff_dashboard.AlertPriority
import com.davf392.panierlocal.data.staff_dashboard.BasketFormulaSummary
import com.davf392.panierlocal.data.staff_dashboard.DashboardAlert
import com.davf392.panierlocal.data.staff_dashboard.PermanenceSlot
import com.davf392.panierlocal.ui.features.common.DistributionLocationHeader
import com.davf392.panierlocal.ui.features.dashboard.components.AlertCard
import com.davf392.panierlocal.ui.features.dashboard.components.BasketCard
import com.davf392.panierlocal.ui.features.dashboard.components.PermanenceCard
import com.davf392.panierlocal.ui.features.dashboard.components.ReportAlertDialog
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.ui.utils.formatDateTime
import com.davf392.panierlocal.viewmodel.location.Location
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardEvent
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardViewModel
import com.davf392.panierlocal.viewmodel.staff_dashboard.DistributionUiState
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardScreenContent(
    selectedDistribution: DistributionUiState,
    currentLocation: Location,
    locations: List<Location>,
    onLocationSelected: (Location) -> Unit,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var showReportDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            DistributionLocationHeader(
                currentLocation = currentLocation,
                locations = locations,
                onLocationSelected = onLocationSelected
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    val (date, time) = formatDateTime(
                        selectedDistribution.startTime,
                        selectedDistribution.endTime
                    )
                    Column {
                        Text(text = date, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                item {
                    AlertCard(
                        alerts = selectedDistribution.alerts,
                        onResolve = { alert ->
                            onEvent(DashboardEvent.ResolveAlert(alert))
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Alerte résolue")
                            }
                        },
                        onReport = { showReportDialog = true }
                    )
                }
                item { PermanenceCard(selectedDistribution.permanenceSlots) }
                item { BasketCard(selectedDistribution.basketSummaries) }
            }
        }
        
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter)
        )
    }

    if (showReportDialog) {
        ReportAlertDialog(
            onDismiss = { showReportDialog = false },
            onConfirm = { message ->
                onEvent(DashboardEvent.ReportAlert(message, AlertPriority.WARNING))
                showReportDialog = false
            }
        )
    }
}

@Composable
fun DashboardScreen(
    currentLocation: Location,
    locations: List<Location>,
    onLocationSelected: (Location) -> Unit,
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val selectedDistribution by viewModel.uiState.collectAsState()

    val distributionState = selectedDistribution
    if (distributionState == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        DashboardScreenContent(
            selectedDistribution = distributionState,
            currentLocation = currentLocation,
            locations = locations,
            onLocationSelected = onLocationSelected,
            onEvent = viewModel::onEvent,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun PreviewDashboardScreen() {
    PanierLocalTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DashboardScreenContent(
                selectedDistribution = DistributionUiState(
                    id = "dist-001",
                    location = "Le Croiseur (Lyon 7)",
                    startTime = LocalDateTime(2026, 9, 8, 14, 0),
                    endTime = LocalDateTime(2026, 9, 8, 18, 0),
                    permanenceSlots = listOf(
                        PermanenceSlot("p1", "Jean", "Responsable", true)
                    ),
                    basketSummaries = listOf(
                        BasketFormulaSummary("b1", "Mini", 20, 15),
                        BasketFormulaSummary("b2", "Solo", 30, 25)
                    ),
                    alerts = listOf(
                        DashboardAlert("a1", "Alerte Lyon 7", AlertPriority.INFO)
                    )
                ),
                currentLocation = Location("dist-001", "Le Croiseur (Lyon 7)"),
                locations = listOf(
                    Location("dist-001", "Le Croiseur (Lyon 7)"),
                    Location("dist-002", "Cabanes (Lyon 8)")
                ),
                onLocationSelected = {},
                onEvent = {}
            )
        }
    }
}