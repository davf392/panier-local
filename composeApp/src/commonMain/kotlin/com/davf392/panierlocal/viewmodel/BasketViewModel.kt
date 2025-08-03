package com.amap.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.state.BasketUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasketViewModel(
//    private val basketRepository: BasketRepository
) : ViewModel() {

    private val tandemBasket = WeeklyBasketItem(
        name = "Tandem Légumes",
        weekNumber = 15,
        year = 2024,
        totalPrice = 10.90,
        formula = "Tandem",
        productsList = listOf(
            ProductItem(name = "Salade", quantity = 1.0, unit = "pièce", pricePerUnit = 2.50, totalPrice = 4.7, emoji = "🥬"),
            ProductItem(name = "Concombre", quantity = 1.0, unit = "pièce", pricePerUnit = 1.80, totalPrice = 4.7, emoji = "🥒"),
            ProductItem(name = "Oignon blanc", quantity = 200.0, unit = "g", pricePerUnit = 1.60, totalPrice = 4.7, emoji = "🧅"),
            ProductItem(name = "Tomate cerise", quantity = 150.0, unit = "g", pricePerUnit = 1.80, totalPrice = 4.7, emoji = "🍅"),
            ProductItem(name = "Aubergine", quantity = 800.0, unit = "g", pricePerUnit = 3.20, totalPrice = 4.7, emoji = "🍆")
        )
    )
    private val soloBasket = WeeklyBasketItem(
        name = "Formule Solo Fruits",
        weekNumber = 15,
        year = 2024,
        totalPrice = 10.90,
        formula = "Solo",
        productsList = listOf(
            ProductItem(name = "Salade", quantity = 1.0, unit = "pièce", pricePerUnit = 2.50, totalPrice = 4.7, emoji = "🥬"),
            ProductItem(name = "Concombre", quantity = 1.0, unit = "pièce", pricePerUnit = 1.80, totalPrice = 4.7, emoji = "🥒"),
            ProductItem(name = "Oignon blanc", quantity = 200.0, unit = "g", pricePerUnit = 1.60, totalPrice = 4.7, emoji = "🧅"),
            ProductItem(name = "Tomate cerise", quantity = 150.0, unit = "g", pricePerUnit = 1.80, totalPrice = 4.7, emoji = "🍅"),
            ProductItem(name = "Aubergine", quantity = 800.0, unit = "g", pricePerUnit = 3.20, totalPrice = 4.7, emoji = "🍆")
        )
    )
    private val basketList = listOf(tandemBasket, soloBasket)

    private val _uiState = MutableStateFlow<BasketUiState>(BasketUiState.Loading)
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    // The current basket being displayed (for the HorizontalPager)
    private val _currentBasketIndex = MutableStateFlow(0)
    val currentBasketIndex: StateFlow<Int> = _currentBasketIndex.asStateFlow()

    init {
        fetchBaskets()
    }

    private fun fetchBaskets() {
        viewModelScope.launch {
            _uiState.value = BasketUiState.Loading
            try {
                val baskets = /*basketRepository.getUpcomingBaskets()*/ basketList
                if (baskets.isNotEmpty()) {
                    _uiState.value = BasketUiState.Success(baskets)
                } else {
                    _uiState.value = BasketUiState.Error("No upcoming baskets found.")
                }
            } catch (e: Exception) {
                _uiState.value = BasketUiState.Error("Failed to load baskets: ${e.message}")
            }
        }
    }

    fun onNextBasket() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is BasketUiState.Success) {
                val nextIndex = (_currentBasketIndex.value + 1) % currentState.baskets.size
                _currentBasketIndex.value = nextIndex
            }
        }
    }

    fun onPreviousBasket() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is BasketUiState.Success) {
                val prevIndex = (_currentBasketIndex.value - 1 + currentState.baskets.size) % currentState.baskets.size
                _currentBasketIndex.value = prevIndex
            }
        }
    }

    // This function will be called from the UI when a basket item is clicked
    // It's a placeholder for navigation logic.
    fun onProductItemExchangeClicked(item: ProductItem) {
        // Here you would navigate to the exchange screen, passing the item data.
        // In a real project, this would likely be handled via a navigation event flow
        // or a callback to the parent composable.
        println("Exchange button clicked for: ${item.name}")
    }

    // This function is for the "View History" button
    fun onViewHistoryClicked() {
        // Here you would navigate to the history screen.
        println("View history button clicked.")
    }
}