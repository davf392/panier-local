package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BasketContentSection(
    items: List<ProductItem> = emptyList(),
    onExchangeClicked: (ProductItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            ProductBasketSection(
                item = item,
                onExchangeClicked = onExchangeClicked
            )
        }
    }
}

@Preview
@Composable
fun BasketContentSectionPreview() {
    PanierLocalTheme {
        BasketContentSection(
            items = listOf(
                ProductItem(name = "Salade", quantity = 1.0, unit = "pièce", pricePerUnit = 2.50, totalPrice = 2.50, emoji = "🥬"),
                ProductItem(name = "Concombre", quantity = 1.0, unit = "pièce", pricePerUnit = 1.80, totalPrice = 1.80, emoji = "🥒"),
                ProductItem(name = "Oignon blanc", quantity = 200.0, unit = "g", pricePerUnit = 1.60, totalPrice = 1.60, emoji = "🧅"),
                ProductItem(name = "Tomate cerise", quantity = 150.0, unit = "g", pricePerUnit = 1.80, totalPrice = 1.80, emoji = "🍅"),
                ProductItem(name = "Aubergine", quantity = 800.0, unit = "g", pricePerUnit = 3.20, totalPrice = 3.20, emoji = "🍆")
            ),
            onExchangeClicked = {},
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}