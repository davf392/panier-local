package com.davf392.panierlocal.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.state.BasketUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasketViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<BasketUiState>(BasketUiState.Loading)
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    init {
        fetchBaskets()
    }

    private fun fetchBaskets() {
        viewModelScope.launch {
//            _uiState.value = BasketUiState.Loading
            try {
//                delay(1000)
                initWeeklyBasketList().let { baskets ->
                    _uiState.value = if (baskets.isNotEmpty()) {
                        BasketUiState.Success(baskets)
                    } else {
                        BasketUiState.Error("Aucun panier disponible cette semaine")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = BasketUiState.Error(
                    "Une erreur est survenue lors du chargement des paniers : ${e.message}"
                )
            }
        }
    }

    fun onProductItemExchangeClicked(item: ProductItem) {
        // Here you would navigate to the exchange screen, passing the item data
        println("Exchange button clicked for: ${item.name}")
    }

    fun onViewHistoryClicked() {
        // Here you would navigate to the history screen.
        println("View history button clicked.")
    }

    private fun initWeeklyBasketList(): List<WeeklyBasketItem> {
        return mutableStateListOf(
            WeeklyBasketItem(
                id = "TANDEM_LEGUMES_001",
                name = "Tandem Légumes",
                weekNumber = 15,
                year = 2024,
                totalPrice = 10.90,
                formula = "Tandem",
                productsList = listOf(
                    ProductItem(id = "1", name = "Salade", quantity = 1.0, unit = "pièce", pricePerUnit = 2.50, totalPrice = 2.50, emoji = "🥬"),
                    ProductItem(id = "2", name = "Concombre", quantity = 1.0, unit = "pièce", pricePerUnit = 1.80, totalPrice = 1.80, emoji = "🥒"),
                    ProductItem(id = "3", name = "Oignon blanc", quantity = 200.0, unit = "g", pricePerUnit = 1.60, totalPrice = 1.60, emoji = "🧅"),
                    ProductItem(id = "4", name = "Tomate", quantity = 150.0, unit = "g", pricePerUnit = 1.80, totalPrice = 1.80, emoji = "🍅"),
                    ProductItem(id = "5", name = "Aubergine", quantity = 800.0, unit = "g", pricePerUnit = 3.20, totalPrice = 3.20, emoji = "🍆")
                )
            ),
            WeeklyBasketItem(
                id = "SOLO_FRUITS_001",
                name = "Solo Fruits",
                weekNumber = 15,
                year = 2024,
                totalPrice = 8.50,
                formula = "Solo",
                productsList = listOf(
                    ProductItem(id = "6", name = "Banane", quantity = 500.0, unit = "g", pricePerUnit = 3.0, totalPrice = 1.50, emoji = "🍌"),
                    ProductItem(id = "7", name = "Pomme", quantity = 1.0, unit = "kg", pricePerUnit = 3.50, totalPrice = 3.50, emoji = "🍎"),
                    ProductItem(id = "8", name = "Poire", quantity = 500.0, unit = "g", pricePerUnit = 7.0, totalPrice = 3.50, emoji = "🍐")
                )
            )
        )
    }

    fun getAvailableProducts(): List<ExchangeItem> {
        return listOf(
            ExchangeItem(id = "1", name = "Salade", emoji = "🥬", pricePerKg = 2.50),
            ExchangeItem(id = "2", name = "Concombre", emoji = "🥒", pricePerKg = 1.00),
            ExchangeItem(id = "3", name = "Oignon blanc", emoji = "🧅", pricePerKg = 1.60),
            ExchangeItem(id = "4", name = "Tomate", emoji = "🍅", pricePerKg = 1.80),
            ExchangeItem(id = "5", name = "Aubergine", emoji = "🍆", pricePerKg = 3.20),
            ExchangeItem(id = "6", name = "Banane", emoji = "🍌", pricePerKg = 1.50),
            ExchangeItem(id = "7", name = "Pomme", emoji = "🍎", pricePerKg = 3.50),
            ExchangeItem(id = "8", name = "Poire", emoji = "🍐", pricePerKg = 3.50),
        )
    }

    fun getItemToExchange(itemId: String?): ProductItem? {
        if (itemId == null) return null
        val allBaskets = initWeeklyBasketList()

        allBaskets.forEach { basket ->
            basket.productsList.forEach { product ->
                if (product.id == itemId) {
                    return product
                }
            }
        }
        return null
    }

}