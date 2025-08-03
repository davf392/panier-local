package com.davf392.panierlocal.state

import com.davf392.panierlocal.data.ExchangeItem

sealed interface ExchangeSimulationState {
    data class InputWeight(
        val itemToExchange: ExchangeItem,
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