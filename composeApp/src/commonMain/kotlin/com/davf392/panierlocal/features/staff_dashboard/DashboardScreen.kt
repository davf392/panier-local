package com.davf392.panierlocal.features.staff_dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.DistributionLocation
import com.davf392.panierlocal.navigation.DistributionContext
import com.davf392.panierlocal.navigation.LocalDistributionContext
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

// --- Previews ---

@Preview
@Composable
private fun DashboardScreenLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            DashboardScreenContent(
                selectedDistribution = previewDistributionUiState,
                distributionContext = previewDistributionContext,
                onEvent = {}
            )
        }
    }
}

@Preview
@Composable
private fun DashboardScreenDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            DashboardScreenContent(
                selectedDistribution = previewDistributionUiState,
                distributionContext = previewDistributionContext,
                onEvent = {}
            )
        }
    }
}

private val previewDistributionUiState = DistributionUiState(
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
)

private val previewDistributionContext = DistributionContext(
    currentLocation = DistributionLocation("dist-001", "Le Croiseur (Lyon 7)"),
    locations = listOf(
        DistributionLocation("dist-001", "Le Croiseur (Lyon 7)"),
        DistributionLocation("dist-002", "Cabanes (Lyon 8)")
    ),
    onLocationSelected = {},
    startTime = LocalDateTime(2026, 9, 8, 14, 0),
    endTime = LocalDateTime(2026, 9, 8, 18, 0)
)