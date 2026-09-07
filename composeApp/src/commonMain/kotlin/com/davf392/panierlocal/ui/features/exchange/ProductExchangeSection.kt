package com.davf392.panierlocal.ui.features.exchange_simulator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.toLabel
import com.davf392.panierlocal.ui.features.ProductGridItemCard
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductExchangeSection(
    item: ExchangeItem = ExchangeItem(),
    onProductSelected: (ExchangeItem) -> Unit = {},
    isSelected: Boolean = false,
) {
    ProductGridItemCard(
        item = item,
        onClick = { onProductSelected(item) },
        isSelected = isSelected
    ) { contentColor ->
        Text(
            text = "${com.davf392.panierlocal.formatDecimal(item.pricePerUnit, 2)} €/${item.unit.toLabel(1.0)}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
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
                pricePerUnit = 0.7
            ),
            onProductSelected = {}
        )
    }
}