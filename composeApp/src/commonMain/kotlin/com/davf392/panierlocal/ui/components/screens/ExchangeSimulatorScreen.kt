package com.davf392.panierlocal.ui.components.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.state.ExchangeUiState
import com.davf392.panierlocal.ui.components.PanierLocalTopAppBar
import com.davf392.panierlocal.ui.components.exchange_simulator.ProductSelectionSection
import com.davf392.panierlocal.ui.components.exchange_simulator.ResultDisplaySection
import com.davf392.panierlocal.ui.components.exchange_simulator.WeightDisplaySection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ExchangeStep {
    SELECT_PRODUCT,
    CONFIRMATION
}

@Composable
fun ExchangeSimulatorScreen(
    uiState: ExchangeUiState,
    onProductSelected: (ExchangeItem) -> Unit = {},
    onBackClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(ExchangeStep.SELECT_PRODUCT) }

    Scaffold(
        topBar = {
            PanierLocalTopAppBar(
                title = "Simulateur d'échange",
                onBackClicked = {
                    if (step == ExchangeStep.CONFIRMATION) {
                        step = ExchangeStep.SELECT_PRODUCT
                    } else {
                        onBackClicked()
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Progress Indicator
            LinearProgressIndicator(
                progress = { if (step == ExchangeStep.SELECT_PRODUCT) 0.5f else 1.0f },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            // Header
            WeightDisplaySection(
                item = uiState.itemToExchange,
                weightGrams = uiState.returnedWeightGrams
            )

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "ExchangeStepAnimation"
            ) { targetStep ->
                when (targetStep) {
                    ExchangeStep.SELECT_PRODUCT -> {
                        ProductSelectionSection(
                            availableProducts = uiState.availableProducts,
                            onProductSelected = { product ->
                                onProductSelected(product)
                                step = ExchangeStep.CONFIRMATION
                            }
                        )
                    }
                    ExchangeStep.CONFIRMATION -> {
                        uiState.selectedProduct?.let { selectedProduct ->
                            ResultDisplaySection(
                                exchangedProduct = selectedProduct,
                                maxWeightGrams = uiState.exchangeResult
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExchangeSimulatorScreenPreview() {
    PanierLocalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ExchangeSimulatorScreen(
                uiState = ExchangeUiState(
                    itemToExchange = ProductItem(
                        id = "6",
                        name = "Banane",
                        quantity = 500.0,
                        unit = ProductUnit.PIECE,
                        pricePerUnit = 3.0,
                        totalPrice = 1.50,
                        emoji = "🍌"
                    ),
                    availableProducts = listOf(
                        ExchangeItem(id = "7", name = "Pomme", emoji = "🍎", pricePerUnit = 3.50),
                        ExchangeItem(id = "8", name = "Poire", emoji = "🍐", pricePerUnit = 3.50)
                    ),
                    returnedWeightGrams = 150,
                    selectedProduct = ExchangeItem(id = "7", name = "Pomme", emoji = "🍎", pricePerUnit = 3.50),
                    exchangeResult = 200
                ),
                onProductSelected = {},
            )
        }
    }
}