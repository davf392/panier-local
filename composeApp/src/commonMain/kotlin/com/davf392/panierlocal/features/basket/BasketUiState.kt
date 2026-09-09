package com.davf392.panierlocal.features.basket

import com.davf392.panierlocal.data.WeeklyBasketItem

data class BasketUiState(
    val baskets: List<WeeklyBasketItem> = emptyList(),
    val locationName: String = ""
)