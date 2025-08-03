package com.davf392.panierlocal.data

import com.davf392.panierlocal.formatDecimal

data class ProductItem(
    val name: String,
    val quantity: Double,
    val unit: String,
    val pricePerUnit: Double,
    val totalPrice: Double,
    val emoji: String
) {
    val displayQuantity: String
        get() = when {
            quantity == 1.0 && unit == "pièce" -> "1 pièce"
            unit == "pièce" -> "${quantity.toInt()} pièces"
            unit == "g" -> "${quantity.toInt()} g"
            unit == "kg" -> "${quantity} kg"
            else -> "$quantity $unit"
        }

    val displayPrice: String
        get() = "${formatDecimal(totalPrice, 2)} €"
}