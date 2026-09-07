package com.davf392.panierlocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.WeeklyBasketItem
import com.davf392.panierlocal.repository.ProductRepository
import com.davf392.panierlocal.state.BasketUiState
import com.davf392.panierlocal.state.ExchangeUiState
import com.davf392.panierlocal.usecase.CalculateExchangeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BasketViewModel(
    private val productRepository: ProductRepository,
    private val calculateExchangeUseCase: CalculateExchangeUseCase
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
                _uiState.update { it -> it.copy(baskets = baskets) }
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

    fun setReturnedQuantity(quantity: Int) {
        _exchangeUiState.update { it ->
            it?.copy(returnedWeightGrams = quantity)
        }
        if (_exchangeUiState.value?.selectedProduct != null) {
            calculateResult()
        }
    }

    fun selectProduct(product: ExchangeItem) {
        _exchangeUiState.update { it ->
            it?.copy(selectedProduct = product)
        }
        calculateResult()
    }

    fun getItemToExchange(itemId: String?): ProductItem? {
        if (itemId == null) return null

        allBaskets.forEach { basket ->
            basket.productsList.forEach { product ->
                if (product.id == itemId) {
                    return product
                }
            }
        }
        return null
    }

    fun navigateToExchangeSimulatorScreen(itemId: String?) {
        _exchangeUiState.update { it ->
            it?.copy(
                itemToExchange = getItemToExchange(itemId) ?: ProductItem()
            )
        }
    }

    private fun calculateResult() {
        val state = _exchangeUiState.value ?: return
        val itemToExchange = state.itemToExchange
        val selectedProduct = state.selectedProduct ?: return

        viewModelScope.launch {
            val maxQuantity = calculateExchangeUseCase.execute(
                itemToExchange = itemToExchange,
                exchangedAgainst = selectedProduct,
                returnedQuantity = itemToExchange.quantity
            )

            _exchangeUiState.update { it -> it?.copy(
                    exchangeResult = maxQuantity?.toInt() ?: 0
                )
            }
        }
    }
}