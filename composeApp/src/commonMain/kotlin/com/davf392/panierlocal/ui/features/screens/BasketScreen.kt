package com.davf392.panierlocal.ui.features.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.state.BasketUiState
import com.davf392.panierlocal.ui.features.BasketHistoryButton
import com.davf392.panierlocal.ui.features.WeeklyBasketDeliverySection
import com.davf392.panierlocal.ui.features.common.providers.BasketUiStatePreviewProvider
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter


@Composable
fun BasketScreen(
    uiState: BasketUiState = BasketUiState(),
    onExchangeClicked: (ProductItem) -> Unit = {},
    onViewHistoryClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(top = 52.dp).fillMaxSize()) {
        WeeklyBasketDeliverySection(
            items = uiState.baskets,
            modifier = Modifier.weight(1f),
            onExchangeClicked = onExchangeClicked
        )
        BasketHistoryButton(onViewHistoryClicked = onViewHistoryClicked)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun BasketScreenLightPreview(
    @PreviewParameter(BasketUiStatePreviewProvider::class) uiState: BasketUiState
) {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BasketScreen(uiState = uiState)
        }
    }
}

@Preview
@Composable
fun BasketScreenDarkPreview(
    @PreviewParameter(BasketUiStatePreviewProvider::class) uiState: BasketUiState
) {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BasketScreen(uiState = uiState)
        }
    }
}