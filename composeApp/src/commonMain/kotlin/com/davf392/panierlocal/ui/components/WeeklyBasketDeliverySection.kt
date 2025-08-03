package com.davf392.panierlocal.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WeeklyBasketDeliverySection(
    items: List<WeeklyBasketItem> = emptyList(),
    onExchangeClicked: (ProductItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var expandedBasketId by remember { mutableStateOf<String?>(null) }

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
                }
            )
            if (expandedBasketId == item.id) {
                Spacer(modifier = Modifier.height(8.dp))
                BasketContentSection(
                    items = item.productsList,
                    onExchangeClicked = onExchangeClicked
                )
            }
        }
    }
}

@Preview
@Composable
fun WeeklyBasketDeliverySectionPreview() {
    PanierLocalTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WeeklyBasketDeliverySection(
                items = listOf(
                    WeeklyBasketItem(id = "1", name = "Tandem Légumes", weekNumber = 15, year = 2024, totalPrice = 10.90, formula = "Tandem", productsList = emptyList()),
                    WeeklyBasketItem(id = "2", name = "Solo Fruits", weekNumber = 15, year = 2024, totalPrice = 7.80, formula = "Solo", productsList = emptyList())
                )
            )
        }
    }
}