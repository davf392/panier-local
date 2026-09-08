package com.davf392.panierlocal.state

import com.davf392.panierlocal.data.WeeklyBasketItem

data class BasketUiState(
    val baskets: List<WeeklyBasketItem> = emptyList(),
    val locationName: String = ""
)