package com.davf392.panierlocal.data

import com.davf392.panierlocal.formatDecimal

data class ProductItem(
    override val id: String = "",
    override val name: String = "",
    override val emoji: String = "",
    override val unit: ProductUnit? = null,
    override val pricePerUnit: Double = 0.0,
    val quantity: Double = 0.0,
    val totalPrice: Double = 0.0
) : CommonProduct {
    val displayQuantity: String
        get() = when {
            quantity == 1.0 && unit == ProductUnit.PIECE -> "1 pièce"
            unit == ProductUnit.PIECE -> "${quantity.toInt()} pièces"
            unit == ProductUnit.GRAM -> "${quantity.toInt()} g"
            unit == ProductUnit.KILOGRAM -> "$quantity kg"
            else -> "$quantity $unit"
        }

    val displayPrice: String
        get() = "${formatDecimal(totalPrice, 2)} €"
}