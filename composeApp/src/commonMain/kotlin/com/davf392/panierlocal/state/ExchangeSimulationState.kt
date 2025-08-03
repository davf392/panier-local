package com.davf392.panierlocal.state

import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem

sealed interface ExchangeSimulationState {
    data class InputWeight(
        val itemToExchange: ProductItem,
        val defaultWeightGrams: Int
    ) : ExchangeSimulationState

    data class SelectProduct(
        val itemToExchange: ExchangeItem,
        val returnedWeightGrams: Int,
        val availableProducts: List<ExchangeItem>
    ) : ExchangeSimulationState

    data class ShowResult(
        val itemToExchange: ExchangeItem,
        val returnedWeightGrams: Int,
        val exchangedProduct: ExchangeItem,
        val maxWeightGrams: Int
    ) : ExchangeSimulationState
}