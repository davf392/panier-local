package com.davf392.panierlocal.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductExchangeSection(
    item: ExchangeItem = ExchangeItem(),
    onProductSelected: (ExchangeItem) -> Unit = {},
    isSelected: Boolean = false,
) {
    ProductItemCard(
        item = item,
        onClick = { onProductSelected(item) },
        isSelected = isSelected
    ) { contentColor ->
        Text(
            text = "${item.pricePerKg} €/kg",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Preview
@Composable
fun ProductExchangeSectionPreview() {
    PanierLocalTheme {
        ProductExchangeSection(
            item = ExchangeItem(
                name = "Patate",
                emoji = "🥔",
                pricePerKg = 0.7
            ),
            onProductSelected = {}
        )
    }
}