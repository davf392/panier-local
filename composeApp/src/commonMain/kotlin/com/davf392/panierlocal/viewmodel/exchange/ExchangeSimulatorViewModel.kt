package com.davf392.panierlocal.viewmodel.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.repository.ProductRepository
import com.davf392.panierlocal.state.ExchangeUiState
import com.davf392.panierlocal.usecase.CalculateExchangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExchangeSimulatorViewModel(
    private val repository: ProductRepository,
    private val calculateExchangeUseCase: CalculateExchangeUseCase
) : ViewModel() {

    private val _exchangeUiState = MutableStateFlow<ExchangeUiState?>(null)
    val exchangeUiState: StateFlow<ExchangeUiState?> = _exchangeUiState.asStateFlow()

    fun setReturnedQuantity(quantity: Int) {
        _exchangeUiState.update { it?.copy(returnedWeightGrams = quantity) }
        if (_exchangeUiState.value?.selectedProduct != null) {
            calculateResult()
        }
    }

    fun selectProduct(product: ExchangeItem) {
        _exchangeUiState.update { it?.copy(selectedProduct = product) }
        calculateResult()
    }

    fun navigateToExchangeSimulatorScreen(itemId: String?) {
        if (itemId == null) return
        
        viewModelScope.launch {
            val itemToExchange = repository.getProductById(itemId) ?: ProductItem()
            val availableProducts = repository.getAvailableProductsForExchange(itemToExchange)
            
            _exchangeUiState.update { currentState ->
                currentState?.copy(
                    itemToExchange = itemToExchange,
                    availableProducts = availableProducts,
                    returnedWeightGrams = itemToExchange.quantity.toInt()
                ) ?: ExchangeUiState(
                    itemToExchange = itemToExchange,
                    availableProducts = availableProducts,
                    returnedWeightGrams = itemToExchange.quantity.toInt(),
                    exchangeResult = 0
                )
            }
        }
    }

    private fun calculateResult() {
        val state = _exchangeUiState.value ?: return
        val selectedProduct = state.selectedProduct ?: return
        
        viewModelScope.launch {
            val resultAsInt = withContext(Dispatchers.Default) {
                calculateExchangeUseCase.execute(
                    itemToExchange = state.itemToExchange,
                    exchangedAgainst = selectedProduct,
                    returnedQuantity = state.returnedWeightGrams.toDouble()
                )
            }
            _exchangeUiState.update { it?.copy(exchangeResult = resultAsInt) }
        }
    }
}
