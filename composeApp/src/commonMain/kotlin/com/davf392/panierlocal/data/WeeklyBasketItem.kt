package com.davf392.panierlocal.data

import com.davf392.panierlocal.formatDecimal

data class WeeklyBasketItem(
    val name: String,
    val weekNumber: Int,
    val year: Int,
    val formula: String,
    val totalPrice: Double,
    val productsList: List<ProductItem>
) {
    val displayWeek: String
        get() = "Semaine $weekNumber ($year)"
    
    val displayTotalPrice: String
        get() = "${formatDecimal(totalPrice, 2)} €"
}

