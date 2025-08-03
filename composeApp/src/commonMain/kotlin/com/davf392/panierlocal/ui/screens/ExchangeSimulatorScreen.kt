package com.davf392.panierlocal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.state.ExchangeSimulationState
import com.davf392.panierlocal.ui.components.ProductSelectionSection
import com.davf392.panierlocal.ui.components.ResultDisplaySection
import com.davf392.panierlocal.ui.components.WeightInputSection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExchangeSimulatorScreen(
    itemToExchange: ExchangeItem,
    itemDefaultWeightGrams: Int,
    availableProducts: List<ExchangeItem>,
    modifier: Modifier = Modifier
) {
    var currentState by remember {
        mutableStateOf<ExchangeSimulationState>(
            ExchangeSimulationState.InputWeight(itemToExchange, itemDefaultWeightGrams)
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Échange de ${itemToExchange.name}",
            fontWeight = FontWeight.Bold
        )
        when (val state = currentState) {
            is ExchangeSimulationState.InputWeight -> {
                WeightInputSection(
                    itemToExchange = state.itemToExchange,
                    defaultWeightGrams = state.defaultWeightGrams,
                    onWeightConfirmed = { weight ->
                        currentState = ExchangeSimulationState.SelectProduct(
                            itemToExchange = state.itemToExchange,
                            returnedWeightGrams = weight,
                            availableProducts = availableProducts
                        )
                    }
                )
            }
            is ExchangeSimulationState.SelectProduct -> {
                ProductSelectionSection(
                    availableProducts = state.availableProducts,
                    onProductSelected = { selectedProduct ->
                        val valueToExchange = (state.returnedWeightGrams / 1000.0) * state.itemToExchange.pricePerKg
                        val maxWeightKg = valueToExchange / selectedProduct.pricePerKg
                        val maxWeightGrams = (maxWeightKg * 1000).toInt()

                        currentState = ExchangeSimulationState.ShowResult(
                            itemToExchange = state.itemToExchange,
                            returnedWeightGrams = state.returnedWeightGrams,
                            exchangedProduct = selectedProduct,
                            maxWeightGrams = maxWeightGrams
                        )
                    }
                )
            }
            is ExchangeSimulationState.ShowResult -> {
                ResultDisplaySection(
                    itemToExchange = state.itemToExchange,
                    returnedWeightGrams = state.returnedWeightGrams,
                    exchangedProduct = state.exchangedProduct,
                    maxWeightGrams = state.maxWeightGrams
                )
            }
        }
    }
}

@Preview
@Composable
fun ExchangeSimulatorScreenPreview() {
    PanierLocalTheme {
        ExchangeSimulatorScreen(
            itemToExchange = ExchangeItem(
                id = "1",
                name = "Concombre",
                pricePerKg = 3.45,
                emoji = "🥒"
            ),
            itemDefaultWeightGrams = 300,
            availableProducts = listOf(
                ExchangeItem(id = "1", name = "Patate", pricePerKg = 1.08, emoji = "🥔")
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}