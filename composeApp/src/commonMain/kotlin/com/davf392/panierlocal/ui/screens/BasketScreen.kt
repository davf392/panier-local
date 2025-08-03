package com.amap.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.app.viewmodel.BasketViewModel
import com.davf392.panierlocal.state.BasketUiState
import com.davf392.panierlocal.ui.components.BasketContentSection
import com.davf392.panierlocal.ui.components.WeeklyBasketSection
import com.davf392.panierlocal.ui.theme.PanierLocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    viewModel: BasketViewModel = viewModel(),
    onExchangeClicked: () -> Unit = {},
    onViewHistoryClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val currentBasketIndex by viewModel.currentBasketIndex.collectAsState()
    val baskets = (uiState as BasketUiState.Success).baskets
    val currentBasket = baskets[currentBasketIndex]

    Column(modifier = modifier.padding(top = 52.dp).fillMaxSize()) {
        WeeklyBasketSection(
            basket = currentBasket,
            modifier = Modifier.padding(16.dp)
        )
        BasketContentSection(
            items = currentBasket.productsList,
            onExchangeClicked = {
                // navigate to ExchangeSimulatorScreen
            }
        )
        Button(
            onClick = onExchangeClicked,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Historique des anciens paniers"
            )
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