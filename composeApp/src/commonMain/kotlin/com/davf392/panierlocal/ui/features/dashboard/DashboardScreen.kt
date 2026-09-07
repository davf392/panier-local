package com.davf392.panierlocal.ui.features.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davf392.panierlocal.ui.features.dashboard.components.*
import com.davf392.panierlocal.viewmodel.staff_dashboard.DashboardViewModel
import com.davf392.panierlocal.data.staff_dashboard.*
import com.davf392.panierlocal.ui.features.PanierLocalTopAppBar

import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(
    distributions: List<Distribution>,
    selectedDistribution: Distribution,
    onDistributionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { PanierLocalTopAppBar(title = "AMAP Dashboard") }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedDistribution.location,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Lieu de distribution") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    distributions.forEach { dist ->
                        DropdownMenuItem(
                            text = { Text(dist.location) },
                            onClick = {
                                onDistributionSelected(dist.id)
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Text("Date: ${selectedDistribution.date}") }
                item { AlertCard(selectedDistribution.alerts) }
                item { PermanenceCard(selectedDistribution.permanenceSlots) }
                item { BasketCard(selectedDistribution.basketSummaries) }
            }
        }
    }
}

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
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
            onDistributionSelected = viewModel::selectDistribution
        )
    }
}

@Preview
@Composable
fun PreviewDashboardScreen() {
    val mockData = listOf(
        Distribution(
            "dist-001", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), 
            "Le Croiseur (Lyon 7)", DistributionStatus.PREPARATION,
            listOf(PermanenceSlot("p1", "Jean", "Responsable", true)),
            listOf(BasketFormulaSummary("b1", "Mini", 20, 15)),
            listOf(DashboardAlert("a1", "Alerte Lyon 7", AlertPriority.INFO))
        ),
        Distribution(
            "dist-002", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), 
            "Cabanes (Lyon 8)", DistributionStatus.PREPARATION,
            listOf(PermanenceSlot("p2", "Marie", "Responsable", true)),
            listOf(BasketFormulaSummary("b2", "Solo", 30, 25)),
            emptyList()
        )
    )
    DashboardScreenContent(
        distributions = mockData,
        selectedDistribution = mockData.first(),
        onDistributionSelected = {}
    )
}
