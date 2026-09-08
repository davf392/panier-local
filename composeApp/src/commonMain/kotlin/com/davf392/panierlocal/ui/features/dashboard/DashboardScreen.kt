package com.davf392.panierlocal.ui.features.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davf392.panierlocal.ui.features.dashboard.components.*
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardViewModel
import com.davf392.panierlocal.data.staff_dashboard.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.plus
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant

import com.davf392.panierlocal.viewmodel.staff_dashboard.DistributionUiState
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardEvent
import com.davf392.panierlocal.viewmodel.location.Location
import com.davf392.panierlocal.viewmodel.location.LocationViewModel
import com.davf392.panierlocal.ui.features.common.DistributionLocationHeader
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.ui.utils.formatDateTime
import kotlinx.coroutines.launch

@Composable
fun DashboardScreenContent(
    distributions: List<Distribution>,
    selectedDistribution: DistributionUiState,
    currentLocation: Location,
    locations: List<Location>,
    onLocationSelected: (Location) -> Unit,
    onDistributionSelected: (String) -> Unit,
    onEvent: (DashboardEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Using Box to allow showing SnackbarHost without an inner Scaffold
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                DistributionLocationHeader(
                    currentLocation = currentLocation,
                    locations = locations,
                    onLocationSelected = onLocationSelected
                )
            }

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
        
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter)
        )
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
fun DashboardScreen(
    currentLocation: Location,
    locations: List<Location>,
    onLocationSelected: (Location) -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    val distributions by viewModel.distributions.collectAsState()
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
            distributions = distributions,
            selectedDistribution = selectedDistribution!!,
            currentLocation = currentLocation,
            locations = locations,
            onLocationSelected = onLocationSelected,
            onDistributionSelected = viewModel::selectDistribution,
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
        )
    )

    fun Distribution.toUiState(): DistributionUiState {
        return DistributionUiState(id, location, startTime, endTime, permanenceSlots, basketSummaries, alerts)
    }

    PanierLocalTheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DashboardScreenContent(
                distributions = mockData,
                selectedDistribution = mockData.first().toUiState(),
                currentLocation = Location("dist-001", "Le Croiseur (Lyon 7)"),
                locations = emptyList(),
                onLocationSelected = {},
                onDistributionSelected = {},
                onEvent = {}
            )
        }
    }
}
