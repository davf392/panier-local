package com.davf392.panierlocal.features.basket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.Product
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import com.davf392.panierlocal.data.WeeklyBasketItem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun WeeklyBasketDeliverySection(
    items: List<WeeklyBasketItem> = emptyList(),
    onExchangeClicked: (ProductItem) -> Unit = {},
    initialExpandedBasketId: String? = null,
    modifier: Modifier = Modifier
) {
    var expandedBasketId by remember { mutableStateOf(initialExpandedBasketId) }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        
        items(items) { item ->
            WeeklyBasketSection(
                basket = item,
                isSelected = expandedBasketId == item.id,
                onSelectBasket = { clickedItem ->
                    expandedBasketId = if (expandedBasketId == clickedItem.id) null else clickedItem.id
                },
                onExchangeClicked = onExchangeClicked
            )
        }
    }
}

// --- Previews ---

@Composable
private fun WeeklyBasketDeliverySectionPreviewContent(expandedId: String?) {
    WeeklyBasketDeliverySection(
        items = mockWeeklyBasketItems,
        initialExpandedBasketId = expandedId
    )
}

@Preview
@Composable
private fun WeeklyBasketDeliverySectionLightPreview(
    @PreviewParameter(WeeklyBasketDeliveryPreviewParameterProvider::class) expandedId: String?
) {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            WeeklyBasketDeliverySectionPreviewContent(expandedId = expandedId)
        }
    }
}

@Preview
@Composable
private fun WeeklyBasketDeliverySectionDarkPreview(
    @PreviewParameter(WeeklyBasketDeliveryPreviewParameterProvider::class) expandedId: String?
) {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            WeeklyBasketDeliverySectionPreviewContent(expandedId = expandedId)
        }
    }
}

private val mockProducts = listOf(
    ProductItem(product = Product(name = "Salade", unit = ProductUnit.PIECE, pricePerUnit = 2.5), quantity = 1.0),
    ProductItem(product = Product(name = "Carottes", unit = ProductUnit.KILOGRAM, pricePerUnit = 2.0), quantity = 1.0)
)

private val mockWeeklyBasketItems = listOf(
    WeeklyBasketItem(
        id = "1",
        formula = "Mini",
        expectedCount = 20,
        actualCount = 15,
        productsList = mockProducts
    ),
    WeeklyBasketItem(
        id = "2",
        formula = "Solo",
        expectedCount = 30,
        actualCount = 25,
        productsList = emptyList()
    )
)

class WeeklyBasketDeliveryPreviewParameterProvider : PreviewParameterProvider<String?> {
    override val values: Sequence<String?> = sequenceOf(
        null, // Replié
        "1"   // Déplié
    )
}