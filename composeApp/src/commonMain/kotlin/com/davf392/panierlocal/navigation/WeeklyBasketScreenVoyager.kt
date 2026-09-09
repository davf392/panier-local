package com.davf392.panierlocal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.davf392.panierlocal.features.basket.BasketScreen
import com.davf392.panierlocal.features.basket.BasketViewModelFactory
import com.davf392.panierlocal.features.basket.StaffBasketViewModel
import com.davf392.panierlocal.repository.MockProductRepository

object WeeklyBasketScreenVoyager : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: StaffBasketViewModel = viewModel(
            factory = BasketViewModelFactory(MockProductRepository())
        )
        val uiState by viewModel.uiState.collectAsState()
        BasketScreen(
            uiState = uiState,
            onExchangeClicked = { productItem ->
                navigator.push(ExchangeSimulatorScreenVoyager(productItem.product.id))
            }
        )
    }
}

