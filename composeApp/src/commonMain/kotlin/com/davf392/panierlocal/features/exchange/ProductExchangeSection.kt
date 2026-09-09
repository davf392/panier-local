package com.davf392.panierlocal.features.exchange

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.davf392.panierlocal.core.designsystem.ProductGridItemCard
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.core.utils.formatDecimal
import com.davf392.panierlocal.core.utils.toLabel
import com.davf392.panierlocal.data.Product
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductExchangeSection(
    item: Product = Product(),
    onProductSelected: (Product) -> Unit = {},
    isSelected: Boolean = false,
) {
    ProductGridItemCard(
        item = item,
        onClick = { onProductSelected(item) },
        isSelected = isSelected
    ) { contentColor ->
        Text(
            text = "${formatDecimal(item.pricePerUnit, 2)} €/${item.unit.toLabel(1.0)}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = contentColor
        )
    }
}

// --- Previews ---

@Preview
@Composable
fun ProductExchangeSectionLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface {
            ProductExchangeSection(item = mockItem)
        }
    }
}

@Preview
@Composable
fun ProductExchangeSectionDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface {
            ProductExchangeSection(item = mockItem)
        }
    }
}

private val mockItem = Product(
    name = "Patate",
    pricePerUnit = 0.7
)