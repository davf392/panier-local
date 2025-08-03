package com.davf392.panierlocal.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.ui.components.exchange_simulator.ProductSelectionSection
import com.davf392.panierlocal.ui.components.exchange_simulator.ResultDisplaySection
import com.davf392.panierlocal.ui.components.exchange_simulator.WeightInputSection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExchangeSimulatorScreen(
    itemToExchange: ProductItem,
    availableProducts: List<ExchangeItem> = emptyList(),
    modifier: Modifier = Modifier
) {
    var returnedWeightGrams by remember { mutableStateOf(itemToExchange.quantity.toInt()) }
    var selectedProduct by remember { mutableStateOf<ExchangeItem?>(null) }

    // each time a product is selected we compute the max weight
    val maxWeightGrams = remember(returnedWeightGrams, selectedProduct) {
        if (selectedProduct != null) {
            val valueToExchange = (returnedWeightGrams.toDouble() / 1000.0) * itemToExchange.pricePerUnit
            val maxWeightGrams = (valueToExchange / selectedProduct!!.pricePerKg) * 1000
            maxWeightGrams.toInt()
        } else {
            null
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Échange de ${itemToExchange.name} ${itemToExchange.emoji}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        WeightInputSection(defaultWeightGrams = returnedWeightGrams)
        ProductSelectionSection(
            availableProducts = availableProducts,
            onProductSelected = { product ->
                selectedProduct = product
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // display result only if a product is selected
        AnimatedVisibility(visible = selectedProduct != null) {
            if (selectedProduct != null && maxWeightGrams != null) {
                ResultDisplaySection(
                    itemToExchange = itemToExchange,
                    returnedWeightGrams = returnedWeightGrams,
                    exchangedProduct = selectedProduct!!,
                    maxWeightGrams = maxWeightGrams
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
                itemToExchange = ProductItem(
                    id = "1",
                    name = "Concombre",
                    emoji = "🥒",
                    quantity = 200.0,
                    unit = "g",
                    pricePerUnit = 1.5,
                    totalPrice = 4.2,
                ),
                availableProducts = listOf(
                    ExchangeItem(id = "2", name = "Patate", emoji = "🥔", pricePerKg = 1.08),
                    ExchangeItem(id = "3", name = "Radis", emoji = "🫜", pricePerKg = .95),
                    ExchangeItem(id = "4", name = "Broccoli", emoji = "🥦", pricePerKg = 1.25),
                    ExchangeItem(id = "5", name = "Poivron", emoji = "🫑", pricePerKg = 2.9)
                )
            )
        }
    }
}