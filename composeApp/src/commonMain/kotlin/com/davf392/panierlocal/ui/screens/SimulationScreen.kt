package com.davf392.panierlocal.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.app.ui.components.ExchangeSimulator
import com.amap.app.viewmodel.BasketViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SimulationScreen(
    onBackPressed: () -> Unit,
    viewModel: BasketViewModel = viewModel()
) {
    val exchangeSimulation = viewModel.exchangeSimulation
    val availableProducts = viewModel.getAvailableProductsForExchange()

    ExchangeSimulator(
        exchangeSimulation = exchangeSimulation,
        availableProducts = availableProducts,
        onProductToReturnSelected = { product ->
            viewModel.selectProductToReturn(product)
        },
        onQuantityChanged = { quantity ->
            viewModel.updateReturnedQuantity(quantity)
        },
        onProductToReceiveSelected = { product ->
            viewModel.selectProductToReceive(product)
        },
        onReset = {
            viewModel.resetExchange()
        }
    )
}

@Preview
@Composable
fun SimulationScreenPreview() {
    SimulationScreen({})
}