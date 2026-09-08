package com.davf392.panierlocal.ui.features.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.staff_dashboard.AlertPriority
import com.davf392.panierlocal.data.staff_dashboard.BasketFormulaSummary
import com.davf392.panierlocal.data.staff_dashboard.DashboardAlert
import com.davf392.panierlocal.data.staff_dashboard.PermanenceSlot
import com.davf392.panierlocal.ui.composition.DistributionContext
import com.davf392.panierlocal.ui.composition.LocalDistributionContext
import com.davf392.panierlocal.ui.features.dashboard.components.AlertCard
import com.davf392.panierlocal.ui.features.dashboard.components.BasketCard
import com.davf392.panierlocal.ui.features.dashboard.components.DistributionHeaderCard
import com.davf392.panierlocal.ui.features.dashboard.components.PermanenceCard
import com.davf392.panierlocal.ui.features.dashboard.components.ReportAlertDialog
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.viewmodel.location.Location
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardEvent
import com.davf392.panierlocal.viewmodel.staff_dashboard.DistributionUiState
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardScreen(
    selectedDistribution: DistributionUiState,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val distributionContext = LocalDistributionContext.current

    if (distributionContext != null) {
        DashboardScreenContent(
            selectedDistribution = selectedDistribution,
            distributionContext = distributionContext,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
fun DashboardScreenContent(
    selectedDistribution: DistributionUiState,
    distributionContext: DistributionContext,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var showReportDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DistributionHeaderCard(
                    startTime = selectedDistribution.startTime,
                    endTime = selectedDistribution.endTime,
                    currentLocation = distributionContext.currentLocation,
                    locations = distributionContext.locations,
                    onLocationSelected = distributionContext.onLocationSelected
                )
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
                distributionContext = DistributionContext(
                    currentLocation = Location("dist-001", "Le Croiseur (Lyon 7)"),
                    locations = listOf(
                        Location("dist-001", "Le Croiseur (Lyon 7)"),
                        Location("dist-002", "Cabanes (Lyon 8)")
                    ),
                    onLocationSelected = {},
                    startTime = kotlinx.datetime.LocalDateTime(2026, 9, 8, 14, 0),
                    endTime = kotlinx.datetime.LocalDateTime(2026, 9, 8, 18, 0)
                ),
                onEvent = {}
            )
        }
    }
}