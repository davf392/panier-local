package com.davf392.panierlocal.ui.components.exchange_simulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ExchangeItem
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
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Sélectionnez le produit souhaité :",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
// ... rest of file (keep previews)

@Preview
@Composable
fun ProductSelectionSection() {
    PanierLocalTheme {
        ProductSelectionSection(
            availableProducts = listOf(
                ExchangeItem(name = "Patate", emoji = "🥔", pricePerUnit = 0.7),
                ExchangeItem(name = "Gingembre", emoji = "🫚", pricePerUnit = 3.4),
            ),
            onProductSelected = {},
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}