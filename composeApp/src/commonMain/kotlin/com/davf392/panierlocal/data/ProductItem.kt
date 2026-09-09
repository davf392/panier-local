package com.davf392.panierlocal.data

import com.davf392.panierlocal.core.utils.formatDecimal
import com.davf392.panierlocal.core.utils.toLabel

data class ProductItem(
    val product: Product = Product(),
    val quantity: Double = 0.0
) {
    val totalPrice: Double get() = quantity * product.pricePerUnit

    val displayQuantity: String
        get() = "${quantity.toInt()} ${product.unit.toLabel(quantity)}"

    val displayPrice: String
        get() = "${formatDecimal(totalPrice, 2)} €"
}
