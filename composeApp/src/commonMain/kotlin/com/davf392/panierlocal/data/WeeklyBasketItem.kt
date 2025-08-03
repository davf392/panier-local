package com.davf392.panierlocal.data

import com.davf392.panierlocal.formatDecimal

data class WeeklyBasketItem(
    val id: String = "",
    val name: String = "",
    val weekNumber: Int = 0,
    val year: Int = 0,
    val formula: String = "",
    val totalPrice: Double = 0.0,
    val productsList: List<ProductItem> = emptyList()
) {
    val displayWeek: String
        get() = "Semaine $weekNumber ($year)"
    
    val displayTotalPrice: String
        get() = "${formatDecimal(totalPrice, 2)} €"
}

