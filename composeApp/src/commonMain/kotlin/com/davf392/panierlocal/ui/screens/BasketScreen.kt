package com.amap.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.app.viewmodel.BasketViewModel
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
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
    Column(modifier = modifier.padding(top = 48.dp).fillMaxSize()) {
        WeeklyBasketSection(
            currentBasket,
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
            Text(text = "Historique des anciens paniers")
        }
    }
}

@Preview
@Composable
fun BasketScreenPreview() {
    PanierLocalTheme {
        val basketItems = listOf(
            ProductItem(name = "Salade", quantity = 1.0, unit = "pièce", pricePerUnit = 2.50, totalPrice = 4.7, emoji = "🥬"),
            ProductItem(name = "Concombre", quantity = 1.0, unit = "pièce", pricePerUnit = 1.80, totalPrice = 4.7, emoji = "🥒"),
            ProductItem(name = "Oignon blanc", quantity = 200.0, unit = "g", pricePerUnit = 1.60, totalPrice = 4.7, emoji = "🧅"),
            ProductItem(name = "Tomate cerise", quantity = 150.0, unit = "g", pricePerUnit = 1.80, totalPrice = 4.7, emoji = "🍅"),
            ProductItem(name = "Aubergine", quantity = 800.0, unit = "g", pricePerUnit = 3.20, totalPrice = 4.7, emoji = "🍆")
        )
        val weeklyBasket = WeeklyBasketItem(
            name = "Tandem Légumes",
            weekNumber = 15,
            year = 2024,
            totalPrice = 10.90,
            formula = "Tandem",
            productsList = basketItems,
        )

        BasketScreen(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        )
    }
}