package com.davf392.panierlocal.ui.features.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.state.ExchangeUiState
import com.davf392.panierlocal.ui.features.exchange_simulator.ProductSelectionSection
import com.davf392.panierlocal.ui.features.exchange_simulator.ResultDisplaySection
import com.davf392.panierlocal.ui.features.exchange_simulator.WeightDisplaySection
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
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(ExchangeStep.SELECT_PRODUCT) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.selectedProduct?.let { selectedProduct ->
                            ResultDisplaySection(
                                exchangedProduct = selectedProduct,
                                maxWeightGrams = uiState.exchangeResult
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        OutlinedButton(
                            onClick = { step = ExchangeStep.SELECT_PRODUCT },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Modifier ma sélection")
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
