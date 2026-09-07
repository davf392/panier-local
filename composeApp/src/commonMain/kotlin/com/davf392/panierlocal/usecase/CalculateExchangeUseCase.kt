package com.davf392.panierlocal.usecase

import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit

class CalculateExchangeUseCase {
    fun execute(
        itemToExchange: ProductItem,
        exchangedAgainst: ExchangeItem,
        returnedQuantity: Double
    ): Int {
        // Price of returning item (e.g., 200g of Oignon @ 1.60/kg = 0.32€)
        val valueOfReturnedItem: Double = when (itemToExchange.unit) {
            ProductUnit.PIECE -> returnedQuantity * itemToExchange.pricePerUnit
            ProductUnit.GRAM -> (returnedQuantity / 1000.0) * itemToExchange.pricePerUnit
            ProductUnit.KILOGRAM -> returnedQuantity * itemToExchange.pricePerUnit
            null -> 0.0
        }

        // Quantity of new item
        val maxQuantityToTake: Double = when (exchangedAgainst.unit) {
            ProductUnit.PIECE -> valueOfReturnedItem / exchangedAgainst.pricePerUnit
            // For KILOGRAM, we want (value / pricePerKg) * 1000 to get grams
            ProductUnit.GRAM -> (valueOfReturnedItem / exchangedAgainst.pricePerUnit) * 1000.0
            ProductUnit.KILOGRAM -> (valueOfReturnedItem / exchangedAgainst.pricePerUnit) * 1000.0 // Corrected: return grams for KILOGRAM as well if needed, or stick to kg?
            null -> 0.0
        }

        // Rounding logic: Use floor to only count whole units affordable with the returned value
        return kotlin.math.floor(maxQuantityToTake).toInt()
    }
}