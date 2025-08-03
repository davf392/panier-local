package com.davf392.panierlocal.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.state.BasketUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasketViewModel: ViewModel() {

    private val _basketList = MutableStateFlow<List<WeeklyBasketItem>>(emptyList())
    val basketList: StateFlow<List<WeeklyBasketItem>> = _basketList.asStateFlow()

    private val _uiState = MutableStateFlow<BasketUiState>(BasketUiState.Loading)
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    // The current basket being displayed
    private val _currentBasketIndex = MutableStateFlow(0)
    val currentBasketIndex: StateFlow<Int> = _currentBasketIndex.asStateFlow()

    init {
        fetchBaskets()
    }

    private fun fetchBaskets() {
        viewModelScope.launch {
//            _uiState.value = BasketUiState.Loading
            try {
//                delay(1000)
                val baskets = initWeeklyBasketList()
                if (baskets.isNotEmpty()) {
                    _uiState.value = BasketUiState.Success(baskets)
                } else {
                    _uiState.value = BasketUiState.Error("Aucun panier disponible cette semaine")
                }
            } catch (e: Exception) {
                _uiState.value = BasketUiState.Error(
                    "Une erreur est survenue lors du chargement des paniers : ${e.message}"
                )
            }
        }
    }

    fun onSelectBasketItem(index: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is BasketUiState.Success) {
                val nextIndex = (_currentBasketIndex.value + 1) % currentState.baskets.size
                _currentBasketIndex.value = nextIndex
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
                    ProductItem(name = "Salade", quantity = 1.0, unit = "pièce", pricePerUnit = 2.50, totalPrice = 2.50, emoji = "🥬"),
                    ProductItem(name = "Concombre", quantity = 1.0, unit = "pièce", pricePerUnit = 1.80, totalPrice = 1.80, emoji = "🥒"),
                    ProductItem(name = "Oignon blanc", quantity = 200.0, unit = "g", pricePerUnit = 1.60, totalPrice = 1.60, emoji = "🧅"),
                    ProductItem(name = "Tomate", quantity = 150.0, unit = "g", pricePerUnit = 1.80, totalPrice = 1.80, emoji = "🍅"),
                    ProductItem(name = "Aubergine", quantity = 800.0, unit = "g", pricePerUnit = 3.20, totalPrice = 3.20, emoji = "🍆")
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
                    ProductItem(name = "Banane", quantity = 500.0, unit = "g", pricePerUnit = 3.0, totalPrice = 1.50, emoji = "🍌"),
                    ProductItem(name = "Pomme", quantity = 1.0, unit = "kg", pricePerUnit = 3.50, totalPrice = 3.50, emoji = "🍎"),
                    ProductItem(name = "Poire", quantity = 500.0, unit = "g", pricePerUnit = 7.0, totalPrice = 3.50, emoji = "🍐")
                )
            )
        )
    }
}