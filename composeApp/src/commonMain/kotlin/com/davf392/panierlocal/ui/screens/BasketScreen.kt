package com.davf392.panierlocal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.state.BasketUiState
import com.davf392.panierlocal.ui.components.BasketHistoryButton
import com.davf392.panierlocal.ui.components.WeeklyBasketDeliverySection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import com.davf392.panierlocal.viewmodel.BasketViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    viewModel: BasketViewModel = viewModel(),
    onExchangeClicked: (ProductItem) -> Unit = {},
    onViewHistoryClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is BasketUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is BasketUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
        }
        is BasketUiState.Success -> {
            Column(modifier = modifier.padding(top = 52.dp).fillMaxSize()) {
                WeeklyBasketDeliverySection(
                    items = state.baskets,
                    modifier = Modifier.weight(1f),
                    onExchangeClicked = onExchangeClicked
                )
                BasketHistoryButton(onViewHistoryClicked = onViewHistoryClicked)
            }
        }
    }
}

@Preview
@Composable
fun BasketScreenLightPreview() {
    PanierLocalTheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BasketScreen()
        }
    }
}

@Preview
@Composable
fun BasketScreenDarkPreview() {
    PanierLocalTheme(useDarkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BasketScreen()
        }
    }
}