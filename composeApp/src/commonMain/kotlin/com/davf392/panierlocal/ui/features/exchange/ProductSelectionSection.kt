package com.davf392.panierlocal.ui.features.exchange

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
import androidx.compose.material3.Surface
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
    onProductSelected: (ExchangeItem) -> Unit = {},
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

// --- Previews ---

@Preview
@Composable
fun ProductSelectionSectionLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            ProductSelectionSection(availableProducts = mockProducts)
        }
    }
}

@Preview
@Composable
fun ProductSelectionSectionDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            ProductSelectionSection(availableProducts = mockProducts)
        }
    }
}


private val mockProducts = listOf(
    ExchangeItem(name = "Patate", pricePerUnit = 0.7),
    ExchangeItem(name = "Gingembre", pricePerUnit = 3.4),
)
