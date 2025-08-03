package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductExchangeItem(
    product: ExchangeItem = ExchangeItem(),
    onProductSelected: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ProductItemCard(
        item = product,
        modifier = modifier,
//        onClick = { onProductSelected(product) }
    ) {
        // slot is empty because no content on the right side
    }
}

@Preview
@Composable
fun ProductExchangeItem() {
    PanierLocalTheme {
        ProductExchangeItem(
            product = ExchangeItem(
                name = "Patate",
                emoji = "🥔",
                pricePerKg = 0.7
            ),
            onProductSelected = {},
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}