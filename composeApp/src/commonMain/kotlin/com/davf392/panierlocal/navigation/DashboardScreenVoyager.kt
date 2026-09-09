package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.davf392.panierlocal.features.staff_dashboard.DashboardScreen
import com.davf392.panierlocal.features.staff_dashboard.DashboardViewModel
import com.davf392.panierlocal.features.staff_dashboard.DistributionUiState

object DashboardScreenVoyager : Screen {
    @Composable
    override fun Content() {
        val viewModel: DashboardViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        DashboardScreen(
            selectedDistribution = uiState ?: DistributionUiState.empty(),
            onEvent = viewModel::onEvent
        )
    }
}
