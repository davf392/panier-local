package com.davf392.panierlocal.usecase

import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit

class CalculateExchangeUseCase {
    fun execute(
        itemToExchange: ProductItem,
        exchangedAgainst: ExchangeItem,
        returnedQuantity: Double
    ): Double {
        val valueOfReturnedItem: Double = when (itemToExchange.unit) {
            ProductUnit.PIECE -> returnedQuantity * itemToExchange.pricePerUnit
            ProductUnit.GRAM -> (returnedQuantity / 1000.0) * itemToExchange.pricePerUnit
            ProductUnit.KILOGRAM -> returnedQuantity * itemToExchange.pricePerUnit
            null -> 0.0
        }

        val maxQuantityToTake: Double = when (exchangedAgainst.unit) {
            ProductUnit.PIECE -> valueOfReturnedItem / exchangedAgainst.pricePerUnit
            ProductUnit.GRAM -> (valueOfReturnedItem / exchangedAgainst.pricePerUnit) * 1000.0
            ProductUnit.KILOGRAM -> valueOfReturnedItem / exchangedAgainst.pricePerUnit
            null -> 0.0
        }

        return maxQuantityToTake
    }
}