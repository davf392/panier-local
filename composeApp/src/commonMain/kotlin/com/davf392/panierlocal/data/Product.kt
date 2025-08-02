package com.amap.app.data

data class Product(
    val id: String,
    val name: String,
    val emoji: String,
    val quantity: Double,
    val unit: String,
    val pricePerUnit: Double,
    val totalPrice: Double
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
        get() = "%.2f €".format(totalPrice)
}