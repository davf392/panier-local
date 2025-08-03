package com.davf392.panierlocal.state

import com.davf392.panierlocal.data.WeeklyBasketItem

sealed class BasketUiState {
    object Loading : BasketUiState()
    data class Success(val baskets: List<WeeklyBasketItem>) : BasketUiState()
    data class Error(val message: String) : BasketUiState()
}