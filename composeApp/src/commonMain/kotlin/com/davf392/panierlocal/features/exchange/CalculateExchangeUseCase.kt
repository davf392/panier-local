package com.davf392.panierlocal.features.exchange

import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem
import com.davf392.panierlocal.data.ProductUnit
import kotlin.math.floor

/**
 * Use case responsible for calculating the maximum quantity of an [ExchangeItem]
 * a user can receive in exchange for a returned [ProductItem].
 *
 * The final result is rounded down using [floor] to ensure the calculated exchange
 * value never exceeds the financial value of the returned item.
 */
class CalculateExchangeUseCase {

    /**
     * Calculates the maximum allowance for the target product ([exchangedAgainst])
     * based on the monetary value of the returned item ([itemToExchange]).
     *
     * @param itemToExchange The original product being returned.
     * @param exchangedAgainst The new product selected for exchange.
     * @param returnedQuantity The quantity of the returned item (in pieces, grams, or kilograms).
     * @return The maximum allowable quantity of the new product (floored to the nearest integer),
     *         expressed in pieces for [ProductUnit.PIECE] or in grams for [ProductUnit.GRAM] and [ProductUnit.KILOGRAM].
     */
    fun execute(
        itemToExchange: ProductItem,
        exchangedAgainst: ExchangeItem,
        returnedQuantity: Double
    ): Int {
        if (exchangedAgainst.pricePerUnit <= 0.0) return 0

        val returnedValue = calculateReturnedValue(item = itemToExchange, quantity = returnedQuantity)
        val maxQuantity = calculateMaxQuantityToTake(availableValue = returnedValue, targetItem = exchangedAgainst)

        return floor(maxQuantity).toInt()
    }

    /**
     * Computes the total monetary value (€) of the returned product based on its measurement unit.
     */
    private fun calculateReturnedValue(
        item: ProductItem,
        quantity: Double
    ): Double = when (item.unit) {
        ProductUnit.PIECE -> quantity * item.pricePerUnit
        ProductUnit.GRAM -> (quantity / GRAMS_PER_KG) * item.pricePerUnit
        ProductUnit.KILOGRAM -> quantity * item.pricePerUnit
        null -> 0.0
    }

    /**
     * Calculates the raw quantity of the target product obtainable for the given monetary value.
     * Weight-based units ([ProductUnit.GRAM] and [ProductUnit.KILOGRAM]) are converted to grams.
     */
    private fun calculateMaxQuantityToTake(
        availableValue: Double,
        targetItem: ExchangeItem
    ): Double {
        val baseQuantity = availableValue / targetItem.pricePerUnit

        return when (targetItem.unit) {
            ProductUnit.PIECE -> baseQuantity
            ProductUnit.GRAM,
            ProductUnit.KILOGRAM -> baseQuantity * GRAMS_PER_KG
            null -> 0.0
        }
    }

    private companion object {
        private const val GRAMS_PER_KG = 1000.0
    }
}