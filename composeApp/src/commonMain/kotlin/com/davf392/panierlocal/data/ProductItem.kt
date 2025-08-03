package com.davf392.panierlocal.data

import com.davf392.panierlocal.formatDecimal

data class ProductItem(
    override val id: String = "",
    override val name: String = "",
    override val emoji: String = "",
    val quantity: Double = 0.0,
    val unit: String = "",
    val pricePerUnit: Double = 0.0,
    val totalPrice: Double = 0.0
) : CommonProduct {
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