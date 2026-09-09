package com.davf392.panierlocal.features.basket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.core.designsystem.providers.BasketUiStatePreviewProvider
import com.davf392.panierlocal.core.designsystem.theme.PanierLocalTheme
import com.davf392.panierlocal.data.DistributionLocation
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.features.staff_dashboard.DistributionHeaderCard
import com.davf392.panierlocal.navigation.DistributionContext
import com.davf392.panierlocal.navigation.LocalDistributionContext
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun BasketScreen(
    uiState: BasketUiState,
    onExchangeClicked: (ProductItem) -> Unit = {},
    onViewHistoryClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val distributionContext = LocalDistributionContext.current

    BasketScreenContent(
        uiState = uiState,
        distributionContext = distributionContext,
        onExchangeClicked = onExchangeClicked,
        onViewHistoryClicked = onViewHistoryClicked,
        modifier = modifier
    )
}

@Composable
private fun BasketScreenContent(
    uiState: BasketUiState,
    distributionContext: DistributionContext?,
    onExchangeClicked: (ProductItem) -> Unit,
    onViewHistoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedBasketId by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        if (distributionContext != null) {
            DistributionHeaderCard(
                startTime = distributionContext.startTime,
                endTime = distributionContext.endTime,
                currentLocation = distributionContext.currentLocation,
                locations = distributionContext.locations,
                onLocationSelected = distributionContext.onLocationSelected,
                modifier = Modifier.padding(16.dp)
            )
        }

        val categories = uiState.baskets.map { it.category }.distinct()
        var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "") }

        if (categories.isNotEmpty()) {
            TabRow(selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0)) {
                categories.forEach { category ->
                    Tab(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        text = { Text(category) }
                    )
                }
            }
        }

        val filteredBaskets = uiState.baskets.filter { it.category == selectedCategory }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredBaskets.size) { index ->
                val basket = filteredBaskets[index]
                WeeklyBasketSection(
                    basket = basket,
                    isSelected = expandedBasketId == basket.id,
                    onSelectBasket = { clickedItem ->
                        expandedBasketId = if (expandedBasketId == clickedItem.id) null else clickedItem.id
                    },
                    onExchangeClicked = onExchangeClicked
                )
            }
        }

        BasketHistoryButton(onViewHistoryClicked = onViewHistoryClicked)
    }
}

// --- Previews ---

@Preview
@Composable
fun BasketScreenLightPreview(
    @PreviewParameter(BasketUiStatePreviewProvider::class) uiState: BasketUiState
) {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            CompositionLocalProvider(LocalDistributionContext provides MockDistributionContext) {
                BasketScreen(uiState)
            }
        }
    }
}

@Preview
@Composable
fun BasketScreenDarkPreview(
    @PreviewParameter(BasketUiStatePreviewProvider::class) uiState: BasketUiState
) {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            CompositionLocalProvider(LocalDistributionContext provides MockDistributionContext) {
                BasketScreen(uiState)
            }
        }
    }
}


private val MockDistributionContext = DistributionContext(
    currentLocation = DistributionLocation("dist-001", "Le Croiseur (Lyon 7)"),
    locations = emptyList(),
    onLocationSelected = {},
    startTime = LocalDateTime(2026, 9, 8, 14, 0),
    endTime = LocalDateTime(2026, 9, 8, 18, 0)
)