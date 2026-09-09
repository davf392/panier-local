package com.davf392.panierlocal.features.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.features.exchange.ExchangeUiState
import com.davf392.panierlocal.repository.MockProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BasketViewModel(
    private val productRepository: MockProductRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(BasketUiState())
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    private val _exchangeUiState = MutableStateFlow<ExchangeUiState?>(null)
    val exchangeUiState: StateFlow<ExchangeUiState?> = _exchangeUiState.asStateFlow()

    private var allBaskets: List<WeeklyBasketItem> = emptyList()

    init {
        fetchBaskets()
    }

    private fun fetchBaskets() {
        viewModelScope.launch {
            productRepository.getWeeklyBasketList().let { baskets ->
                allBaskets = baskets
                _uiState.update { it.copy(baskets = baskets) }
            }
        }
    }

    fun onViewHistoryClicked() {
        println("View history button clicked.")
    }

    fun getItemToExchange(itemId: String?): ProductItem? {
        if (itemId == null) return null

        allBaskets.forEach { basket ->
            basket.productsList.forEach { item ->
                if (item.product.id == itemId) {
                    return item
                }
            }
        }
        return null
    }
}
