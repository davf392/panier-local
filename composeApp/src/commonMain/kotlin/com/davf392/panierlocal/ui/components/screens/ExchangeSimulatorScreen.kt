package com.davf392.panierlocal.ui.components.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.state.ExchangeUiState
import com.davf392.panierlocal.ui.components.exchange_simulator.ProductSelectionSection
import com.davf392.panierlocal.ui.components.exchange_simulator.ResultDisplaySection
import com.davf392.panierlocal.ui.components.exchange_simulator.WeightInputSection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExchangeSimulatorScreen(
    uiState: ExchangeUiState?,
    onProductSelected: (ExchangeItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Échange de ${uiState?.itemToExchange?.name} ${uiState?.itemToExchange?.emoji}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        WeightInputSection(defaultWeightGrams = uiState?.returnedWeightGrams!!)
        ProductSelectionSection(
            availableProducts = uiState.availableProducts,
            onProductSelected = { onProductSelected }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // display result only if a product is selected
        AnimatedVisibility(visible = uiState.selectedProduct != null) {
            uiState.selectedProduct?.let { selectedProduct ->
                ResultDisplaySection(
                    itemToExchange = uiState.itemToExchange,
                    returnedWeightGrams = uiState.returnedWeightGrams,
                    exchangedProduct = selectedProduct,
                    maxWeightGrams = uiState.exchangeResult
                )
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