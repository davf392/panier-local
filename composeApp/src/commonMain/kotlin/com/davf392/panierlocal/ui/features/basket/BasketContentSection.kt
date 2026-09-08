package com.davf392.panierlocal.ui.features.basket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BasketContentSection(
    items: List<ProductItem> = emptyList(),
    onExchangeClicked: (ProductItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        items.forEachIndexed { index, item ->
            ProductBasketSection(
                item = item,
                onExchangeClicked = onExchangeClicked
            )
            if (index < items.size - 1) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}

// --- Previews ---

@Preview
@Composable
fun BasketContentSectionLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            BasketContentSection(
                items = mockBasketItems,
                onExchangeClicked = {}
            )
        }
    }
}

@Preview
@Composable
fun BasketContentSectionDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            BasketContentSection(
                items = mockBasketItems,
                onExchangeClicked = {}
            )
        }
    }
}


private val mockBasketItems = listOf(
    ProductItem(name = "Salade", quantity = 1.0, unit = ProductUnit.PIECE, pricePerUnit = 2.50, totalPrice = 2.50),
    ProductItem(name = "Concombre", quantity = 1.0, unit = ProductUnit.PIECE, pricePerUnit = 1.80, totalPrice = 1.80),
    ProductItem(name = "Oignon blanc", quantity = 200.0, unit = ProductUnit.GRAM, pricePerUnit = 1.60, totalPrice = 1.60),
    ProductItem(name = "Tomate cerise", quantity = 150.0, unit = ProductUnit.GRAM, pricePerUnit = 1.80, totalPrice = 1.80),
    ProductItem(name = "Aubergine", quantity = 800.0, unit = ProductUnit.GRAM, pricePerUnit = 3.20, totalPrice = 3.20)
)