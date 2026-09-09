package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.davf392.panierlocal.features.exchange.CalculateExchangeUseCase
import com.davf392.panierlocal.features.exchange.ExchangeSimulatorScreen
import com.davf392.panierlocal.features.exchange.ExchangeSimulatorViewModel
import com.davf392.panierlocal.repository.MockProductRepository

data class ExchangeSimulatorScreenVoyager(val itemId: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ExchangeSimulatorViewModel = viewModel {
            ExchangeSimulatorViewModel(
                MockProductRepository(),
                CalculateExchangeUseCase()
            )
        }

        LaunchedEffect(itemId) {
            viewModel.navigateToExchangeSimulatorScreen(itemId)
        }

        val exchangeUiState by viewModel.exchangeUiState.collectAsState()
        exchangeUiState?.let { uiState ->
            ExchangeSimulatorScreen(
                uiState = uiState,
                onProductSelected = { product -> viewModel.selectProduct(product) }
            )
        }
    }
}
