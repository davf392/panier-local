package com.davf392.panierlocal.data

import com.amap.app.data.Product
import com.davf392.panierlocal.formatDecimal

data class Basket(
    val id: String,
    val weekNumber: Int,
    val year: Int,
    val formula: String,
    val products: List<Product>,
    val totalPrice: Double
) {
    val displayWeek: String
        get() = "Semaine $weekNumber ($year)"
    
    val displayTotalPrice: String
        get() = "${formatDecimal(totalPrice, 2)} €"
}

data class ExchangeSimulation(
    val productToReturn: Product? = null,
    val returnedQuantity: Double = 0.0,
    val productToReceive: Product? = null,
    val maxQuantityToReceive: Double = 0.0
) {
    val canExchange: Boolean
        get() = productToReturn != null && returnedQuantity > 0 && productToReceive != null
    
    val exchangeMessage: String
        get() = if (canExchange) {
            "Tu peux prendre jusqu'à " +
            "${formatDecimal(maxQuantityToReceive, 1)} " +
            "${productToReceive?.unit} " +
            "de ${productToReceive?.name}"
        } else ""
}
