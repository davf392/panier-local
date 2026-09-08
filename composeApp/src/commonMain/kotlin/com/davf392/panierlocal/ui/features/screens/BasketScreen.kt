package com.davf392.panierlocal.ui.features.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.state.BasketUiState
import com.davf392.panierlocal.ui.features.BasketContentSection
import com.davf392.panierlocal.ui.features.BasketHistoryButton
import com.davf392.panierlocal.ui.features.WeeklyBasketSection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.davf392.panierlocal.ui.features.common.providers.BasketUiStatePreviewProvider
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun BasketScreen(
    uiState: BasketUiState,
    onExchangeClicked: (ProductItem) -> Unit = {},
    onViewHistoryClicked: () -> Unit = {},
    onUpdateCount: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = uiState.baskets.map { it.category }.distinct()
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "") }
    var expandedBasketId by remember { mutableStateOf<String?>(null) }
    val filteredBaskets = remember(uiState.baskets, selectedCategory) {
        uiState.baskets.filter { it.category == selectedCategory }
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (categories.isNotEmpty()) {
            TabRow(selectedTabIndex = categories.indexOf(selectedCategory)) {
                categories.forEach { category ->
                    Tab(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        text = { Text(category) }
                    )
                }
            }
        }
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredBaskets.size) { index ->
                val basket = filteredBaskets[index]
                val isExpanded = expandedBasketId == basket.id
                WeeklyBasketSection(
                    basket = basket,
                    isSelected = expandedBasketId == basket.id,
                    onSelectBasket = { clickedItem ->
                        expandedBasketId = if (expandedBasketId == clickedItem.id) null else clickedItem.id
                    },
                    onUpdateCount = onUpdateCount
                )
                if (isExpanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    BasketContentSection(
                        items = basket.productsList,
                        onExchangeClicked = onExchangeClicked
                    )
                }
            }
        }
        
        BasketHistoryButton(onViewHistoryClicked = onViewHistoryClicked)
    }
}

@Preview
@Composable
fun BasketScreenPreview(
    @PreviewParameter(BasketUiStatePreviewProvider::class) uiState: BasketUiState
) {
    PanierLocalTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            BasketScreen(
                uiState = uiState,
                onUpdateCount = { _, _ -> }
            )
        }
    }
}
