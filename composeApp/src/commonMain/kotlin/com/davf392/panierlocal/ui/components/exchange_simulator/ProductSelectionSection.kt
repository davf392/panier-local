package com.davf392.panierlocal.ui.components.exchange_simulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.ui.components.ProductExchangeSection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductSelectionSection(
    availableProducts: List<ExchangeItem>,
    onProductSelected: (ExchangeItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedExchangedItem by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier.fillMaxWidth().padding(top = 12.dp)
    ) {
        Text(
            text = "Sélectionnez le produit souhaité en échange :",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availableProducts) { product ->
                ProductExchangeSection(
                    item = product,
                    onProductSelected = { clickedProduct ->
                        selectedExchangedItem = clickedProduct.id
                        onProductSelected(clickedProduct)
                    },
                    isSelected = selectedExchangedItem == product.id,
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductSelectionSection() {
    PanierLocalTheme {
        ProductSelectionSection(
            availableProducts = listOf(
                ExchangeItem(name = "Patate", emoji = "🥔", pricePerKg = 0.7),
                ExchangeItem(name = "Gingembre", emoji = "🫚", pricePerKg = 3.4),
            ),
            onProductSelected = {},
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}