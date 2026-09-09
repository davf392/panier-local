package com.davf392.panierlocal.features.exchange

import com.davf392.panierlocal.data.ExchangeItem
import com.davf392.panierlocal.data.ProductItem

data class ExchangeUiState(
    val itemToExchange: ProductItem,
    val availableProducts: List<ExchangeItem>,
    val returnedWeightGrams: Int,
    val selectedProduct: ExchangeItem? = null,
    val exchangeResult: Int,
)