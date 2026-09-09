package com.davf392.panierlocal.core.utils

import com.davf392.panierlocal.data.ProductUnit

fun ProductUnit?.toLabel(quantity: Double = 2.0): String = when (this) {
    ProductUnit.PIECE -> if (quantity == 1.0) "pièce" else "pièces"
    ProductUnit.GRAM -> "g"
    ProductUnit.KILOGRAM -> "kg"
    null -> ""
}

fun formatWeightDisplay(weightGrams: Int, unit: ProductUnit?): String {
    return when (unit) {
        ProductUnit.KILOGRAM -> if (weightGrams < 1000) "$weightGrams g" else "${weightGrams / 1000.0} kg"
        else -> "$weightGrams ${unit.toLabel(weightGrams.toDouble())}"
    }
}
