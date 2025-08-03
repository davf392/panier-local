package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductExchangeSection(
    item: ExchangeItem = ExchangeItem(),
    onProductSelected: (ExchangeItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    ProductItemCard(
        item = item,
        modifier = modifier,
        onClick = { onProductSelected(item) }
    ) {
        Text(
            text = "${item.pricePerKg} €/kg",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun ProductExchangeSection() {
    PanierLocalTheme {
        ProductExchangeSection(
            item = ExchangeItem(
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